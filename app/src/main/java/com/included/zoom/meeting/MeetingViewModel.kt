package com.included.zoom.meeting

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.included.zoom.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import us.zoom.sdk.InMeetingAudioController
import us.zoom.sdk.InMeetingService
import us.zoom.sdk.InMeetingVideoController
import us.zoom.sdk.JoinMeetingOptions
import us.zoom.sdk.JoinMeetingParam4WithoutLogin
import us.zoom.sdk.MeetingParameter
import us.zoom.sdk.MeetingService
import us.zoom.sdk.MeetingServiceListener
import us.zoom.sdk.MeetingSettingsHelper
import us.zoom.sdk.MeetingStatus
import us.zoom.sdk.ZoomError
import us.zoom.sdk.ZoomSDK
import us.zoom.sdk.ZoomSDKInitParams
import us.zoom.sdk.ZoomSDKInitializeListener
import java.time.Instant

/**
 * Logic to join a Zoom meeting is located here.
 *
 * 1. Check if we have already a JWT session token.
 * 2. If not, generate a new JWT session token with client data and meeting ID.
 * 3. Initialize the Zoom SDK with the JWT session token.
 * 4. Join to the meeting and listen to participants changes.
 */
class MeetingViewModel(
    private val applicationContext: Context,
    private val meetingId: String,
    private val meetingPassword: String,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    private val zoomSdk = ZoomSDK.getInstance()

    private val inMeetingService: InMeetingService
        get() = zoomSdk.inMeetingService

    private val meetingService: MeetingService
        get() = zoomSdk.meetingService

    private val meetingAudioController: InMeetingAudioController
        get() = inMeetingService.inMeetingAudioController

    private val meetingVideoController: InMeetingVideoController
        get() = inMeetingService.inMeetingVideoController

    private val meetingSettingsHelper: MeetingSettingsHelper
        get() = zoomSdk.meetingSettingsHelper

    private var sessionToken: String? = null

    init {
        if (!zoomSdk.isInitialized) {
            initZoom()
        } else {
            startMeeting()
        }
    }

    private fun initZoom() {
        var token = sessionToken
        if (token == null) {
            val tokenResult = generateToken(
                clientId = BuildConfig.ZOOM_CLIENT_ID,
                clientSecret = BuildConfig.ZOOM_CLIENT_SECRET,
                meetingId = meetingId
            )

            if (tokenResult.isSuccess) {
                token = tokenResult.getOrThrow()
                sessionToken = token
            } else {
                _state.value = State.Error(
                    tokenResult.exceptionOrNull()
                        ?.message
                        ?: "Generate token failed."
                )
                return
            }
        }

        val params = ZoomSDKInitParams()
        params.jwtToken = token
        params.domain = "zoom.us"
        params.enableLog = true

        zoomSdk.initialize(applicationContext, object : ZoomSDKInitializeListener {
            override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
                Log.d("debug", "errorCode: $errorCode - internalErrorCode: $internalErrorCode")
                if (errorCode == ZoomError.ZOOM_ERROR_SUCCESS) {
                    startMeeting()
                } else {
                    _state.value =
                        State.Error("Zoom SDK initialization failed with error code $errorCode (internal: $internalErrorCode).")
                }
            }

            override fun onZoomAuthIdentityExpired() {
                _state.value = State.Error("Authentication identity is expired.")
            }
        }, params)
    }

    private fun startMeeting() {
        meetingSettingsHelper.isCustomizedMeetingUIEnabled = true

        meetingService.addListener(object : MeetingServiceListener {
            override fun onMeetingStatusChanged(status: MeetingStatus?, p1: Int, p2: Int) {
                if (status == MeetingStatus.MEETING_STATUS_INMEETING) {
                    _state.value = State.Meeting(
                        meetingId,
                        userIds = inMeetingService.inMeetingUserList,
                        isMicrophoneEnabled = meetingAudioController.isMyAudioMuted,
                        isCameraEnabled = meetingVideoController.isMyVideoMuted
                    )
                }
            }

            override fun onMeetingParameterNotification(p0: MeetingParameter?) {}
        })

        val result = meetingService.joinMeetingWithParams(
            applicationContext,
            JoinMeetingParam4WithoutLogin().apply {
                displayName = "Zoom App User"
                meetingNo = meetingId
                password = meetingPassword
            },
            JoinMeetingOptions()
        )

        if (result != ZoomError.ZOOM_ERROR_SUCCESS) {
            _state.value = State.Error("Join meeting failed with error code $result.")
            return
        }

        inMeetingService.addListener(object : NoOpMeetingListener() {

            override fun onMeetingFail(errorCode: Int, internalErrorCode: Int) {
                _state.value =
                    State.Error("Join meeting failed with error code $errorCode (internal: $internalErrorCode).")
            }

            override fun onMeetingUserJoin(userIds: List<Long>) {
                (_state.value as? State.Meeting)?.let { meetingState ->
                    _state.value = meetingState.copy(
                        userIds = meetingState.userIds + userIds.toSet()
                    )
                }
            }

            override fun onMeetingUserLeave(userIds: List<Long>) {
                (_state.value as? State.Meeting)?.let { meetingState ->
                    _state.value = meetingState.copy(
                        userIds = meetingState.userIds - userIds.toSet()
                    )
                }
            }
        })
    }

    /**
     * @see [Zoom auth documentation](https://developers.zoom.us/docs/meeting-sdk/auth/#generate-a-meeting-sdk-jwt)
     */
    private fun generateToken(
        clientId: String,
        clientSecret: String,
        meetingId: String,
    ): Result<String> = runCatching {
        val now = Instant.now().toEpochMilli() / 1000

        val algorithm = Algorithm.HMAC256(clientSecret)
        JWT.create()
            .withHeader(
                mapOf(
                    "alg" to "HS256",
                    "typ" to "JWT"
                )
            )
            .withPayload(
                mapOf(
                    "appKey" to clientId,
                    "mn" to meetingId,
                    "iat" to now,
                    "exp" to now + TWO_DAYS_IN_SECONDS,
                    "tokenExp" to now + TWO_DAYS_IN_SECONDS
                )
            )
            .sign(algorithm)
    }

    fun toggleMicrophone() {
        (_state.value as? State.Meeting)?.let { meetingState ->
            val newMicrophoneEnabledState = !meetingState.isMicrophoneEnabled
            meetingAudioController.muteMyAudio(newMicrophoneEnabledState)
            _state.value = meetingState.copy(isMicrophoneEnabled = newMicrophoneEnabledState)
        }
    }

    fun toggleCamera() {
        (_state.value as? State.Meeting)?.let { meetingState ->
            val newCameraEnabledState = !meetingState.isCameraEnabled
            meetingVideoController.muteMyVideo(newCameraEnabledState)
            _state.value = meetingState.copy(isCameraEnabled = newCameraEnabledState)
        }
    }

    sealed interface State {
        data object Loading : State
        data class Error(val errorDetails: String) : State
        data class Meeting(
            val meetingId: String,
            val userIds: List<Long>,
            val isMicrophoneEnabled: Boolean,
            val isCameraEnabled: Boolean
        ) : State
    }

    companion object {
        private const val TWO_DAYS_IN_SECONDS = 172800
    }
}