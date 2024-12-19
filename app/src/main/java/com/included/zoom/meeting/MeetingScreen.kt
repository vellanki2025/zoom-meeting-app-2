package com.included.zoom.meeting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.included.zoom.MainActivity
import com.included.zoom.R
import us.zoom.sdk.MobileRTCVideoUnitRenderInfo
import us.zoom.sdk.MobileRTCVideoView

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MeetingScreen(
    meetingId: String,
    meetingPassword: String,
    viewModel: MeetingViewModel = MeetingViewModel(
        applicationContext = LocalContext.current.applicationContext,
        meetingId = meetingId,
        meetingPassword = meetingPassword,
    ),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (state) {
                is MeetingViewModel.State.Loading -> {
                    Text("Loading...")
                }

                is MeetingViewModel.State.Error -> {
                    ErrorView(state as MeetingViewModel.State.Error)
                }

                is MeetingViewModel.State.Meeting ->
                    MeetingView(viewModel, state as MeetingViewModel.State.Meeting)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun MeetingView(viewModel: MeetingViewModel, state: MeetingViewModel.State.Meeting) {
    val mainActivity = LocalContext.current as? MainActivity

    Column {
        AndroidView(
            modifier = Modifier.weight(3f),
            factory = { context ->
                MobileRTCVideoView(context.applicationContext).apply {
                    onResume()
                }
            },
            update = { view ->
                view.videoViewManager?.apply {
                    removeAllVideoUnits()
                    addActiveVideoUnit(MobileRTCVideoUnitRenderInfo(0, 0, 100, 100)
                        .apply {
                            is_username_visible = true
                        }
                    )
                }
            },
        )

        AndroidView(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp),
            factory = { context ->
                MobileRTCVideoView(context.applicationContext).apply {
                    onResume()
                }
            },
            update = { view ->
                view.videoViewManager?.apply {
                    removeAllVideoUnits()

                    state.userIds.forEachIndexed { index, userId ->
                        val width = 100 / state.userIds.size
                        addAttendeeVideoUnit(
                            userId,
                            MobileRTCVideoUnitRenderInfo(width * index, 0, width, 100)
                                .apply {
                                    is_username_visible = true
                                }
                        )
                    }
                }
            },
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            IconButton(onClick = {
                viewModel.toggleMicrophone()
            }) {
                Icon(
                    painter = painterResource(if (state.isMicrophoneEnabled) R.drawable.ic_microphone_on else R.drawable.ic_microphone_off),
                    contentDescription = null
                )
            }

            IconButton(onClick = {
                viewModel.toggleCamera()
            }) {
                Icon(
                    painter = painterResource(if (state.isCameraEnabled) R.drawable.ic_camera_on else R.drawable.ic_camera_off),
                    contentDescription = null
                )
            }

            IconButton(onClick = {
                mainActivity?.toggleFullscreen()
            }) {
                Icon(painter = painterResource(R.drawable.ic_fullscreen), contentDescription = null)
            }
        }
    }
}

@Composable
fun ErrorView(state: MeetingViewModel.State.Error) {
    Text(
        color = Color.Red,
        text = "Error: ${state.errorDetails}"
    )
}