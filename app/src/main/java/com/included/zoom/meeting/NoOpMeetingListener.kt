package com.included.zoom.meeting

import us.zoom.sdk.CameraControlRequestResult
import us.zoom.sdk.CameraControlRequestType
import us.zoom.sdk.ChatMessageDeleteType
import us.zoom.sdk.FreeMeetingNeedUpgradeType
import us.zoom.sdk.ICameraControlRequestHandler
import us.zoom.sdk.IMeetingArchiveConfirmHandler
import us.zoom.sdk.IMeetingInputUserInfoHandler
import us.zoom.sdk.IRequestLocalRecordingPrivilegeHandler
import us.zoom.sdk.InMeetingAudioController
import us.zoom.sdk.InMeetingChatController
import us.zoom.sdk.InMeetingChatMessage
import us.zoom.sdk.InMeetingEventHandler
import us.zoom.sdk.InMeetingServiceListener
import us.zoom.sdk.LocalRecordingRequestPrivilegeStatus
import us.zoom.sdk.MobileRTCFocusModeShareType
import us.zoom.sdk.VideoQuality
import us.zoom.sdk.ZoomSDKFileReceiver
import us.zoom.sdk.ZoomSDKFileSender
import us.zoom.sdk.ZoomSDKFileTransferInfo

abstract class NoOpMeetingListener: InMeetingServiceListener {

    override fun onMeetingNeedPasswordOrDisplayName(p0: Boolean, p1: Boolean, p2: InMeetingEventHandler?) {}

    override fun onWebinarNeedRegister(p0: String?) {
        // No-op
    }

    override fun onJoinMeetingNeedUserInfo(p0: IMeetingInputUserInfoHandler?) {
        // No-op
    }

    override fun onJoinWebinarNeedUserNameAndEmail(p0: InMeetingEventHandler?) {
        // No-op
    }

    override fun onWebinarNeedInputScreenName(p0: InMeetingEventHandler?) {
        // No-op
    }

    override fun onMeetingNeedCloseOtherMeeting(p0: InMeetingEventHandler?) {
        // No-op
    }

    override fun onMeetingFail(errorCode: Int, internalErrorCode: Int) {
        // No-op
    }

    override fun onMeetingLeaveComplete(p0: Long) {
        // No-op
    }

    override fun onMeetingUserJoin(userIds: List<Long>) {
        // No-op
    }

    override fun onMeetingUserLeave(userIds: List<Long>) {
        // No-op
    }

    override fun onMeetingUserUpdated(p0: Long) {
        // No-op
    }

    override fun onInMeetingUserAvatarPathUpdated(p0: Long) {
        // No-op
    }

    override fun onMeetingHostChanged(p0: Long) {
        // No-op
    }

    override fun onMeetingCoHostChange(p0: Long, p1: Boolean) {
        // No-op
    }

    override fun onActiveVideoUserChanged(p0: Long) {
        // No-op
    }

    override fun onActiveSpeakerVideoUserChanged(p0: Long) {
        // No-op
    }

    override fun onHostVideoOrderUpdated(p0: MutableList<Long>?) {
        // No-op
    }

    override fun onFollowHostVideoOrderChanged(p0: Boolean) {
        // No-op
    }

    override fun onSpotlightVideoChanged(p0: MutableList<Long>?) {
        // No-op
    }

    override fun onUserVideoStatusChanged(p0: Long, p1: InMeetingServiceListener.VideoStatus?) {
        // No-op
    }

    override fun onSinkMeetingVideoQualityChanged(p0: VideoQuality?, p1: Long) {
        // No-op
    }

    override fun onMicrophoneStatusError(p0: InMeetingAudioController.MobileRTCMicrophoneError?) {
        // No-op
    }

    override fun onUserAudioStatusChanged(p0: Long, p1: InMeetingServiceListener.AudioStatus?) {
        // No-op
    }

    override fun onHostAskUnMute(p0: Long) {
        // No-op
    }

    override fun onHostAskStartVideo(p0: Long) {
        // No-op
    }

    override fun onUserAudioTypeChanged(p0: Long) {
        // No-op
    }

    override fun onMyAudioSourceTypeChanged(p0: Int) {
        // No-op
    }

    override fun onLowOrRaiseHandStatusChanged(p0: Long, p1: Boolean) {
        // No-op
    }

    override fun onChatMessageReceived(p0: InMeetingChatMessage?) {
        // No-op
    }

    override fun onChatMsgDeleteNotification(p0: String?, p1: ChatMessageDeleteType?) {
        // No-op
    }

    override fun onShareMeetingChatStatusChanged(p0: Boolean) {
        // No-op
    }

    override fun onSilentModeChanged(p0: Boolean) {
        // No-op
    }

    override fun onFreeMeetingReminder(p0: Boolean, p1: Boolean, p2: Boolean) {
        // No-op
    }

    override fun onMeetingActiveVideo(p0: Long) {
        // No-op
    }

    override fun onSinkAttendeeChatPriviledgeChanged(p0: Int) {
        // No-op
    }

    override fun onSinkAllowAttendeeChatNotification(p0: Int) {
        // No-op
    }

    override fun onSinkPanelistChatPrivilegeChanged(p0: InMeetingChatController.MobileRTCWebinarPanelistChatPrivilege?) {
        // No-op
    }

    override fun onUserNamesChanged(p0: MutableList<Long>?) {
        // No-op
    }

    override fun onFreeMeetingNeedToUpgrade(p0: FreeMeetingNeedUpgradeType?, p1: String?) {
        // No-op
    }

    override fun onFreeMeetingUpgradeToGiftFreeTrialStart() {
        // No-op
    }

    override fun onFreeMeetingUpgradeToGiftFreeTrialStop() {
        // No-op
    }

    override fun onFreeMeetingUpgradeToProMeeting() {
        // No-op
    }

    override fun onClosedCaptionReceived(p0: String?, p1: Long) {
        // No-op
    }

    override fun onRecordingStatus(p0: InMeetingServiceListener.RecordingStatus?) {
        // No-op
    }

    override fun onLocalRecordingStatus(p0: Long, p1: InMeetingServiceListener.RecordingStatus?) {
        // No-op
    }

    override fun onInvalidReclaimHostkey() {
        // No-op
    }

    override fun onPermissionRequested(p0: Array<out String>?) {
        // No-op
    }

    override fun onAllHandsLowered() {
        // No-op
    }

    override fun onLocalVideoOrderUpdated(p0: MutableList<Long>?) {
        // No-op
    }

    override fun onLocalRecordingPrivilegeRequested(p0: IRequestLocalRecordingPrivilegeHandler?) {
        // No-op
    }

    override fun onSuspendParticipantsActivities() {
        // No-op
    }

    override fun onAllowParticipantsStartVideoNotification(p0: Boolean) {
        // No-op
    }

    override fun onAllowParticipantsRenameNotification(p0: Boolean) {
        // No-op
    }

    override fun onAllowParticipantsUnmuteSelfNotification(p0: Boolean) {
        // No-op
    }

    override fun onAllowParticipantsShareWhiteBoardNotification(p0: Boolean) {
        // No-op
    }

    override fun onMeetingLockStatus(p0: Boolean) {
        // No-op
    }

    override fun onRequestLocalRecordingPrivilegeChanged(p0: LocalRecordingRequestPrivilegeStatus?) {
        // No-op
    }

    override fun onAICompanionActiveChangeNotice(p0: Boolean) {
        // No-op
    }

    override fun onParticipantProfilePictureStatusChange(p0: Boolean) {
        // No-op
    }

    override fun onCloudRecordingStorageFull(p0: Long) {
        // No-op
    }

    override fun onUVCCameraStatusChange(
        p0: String?,
        p1: InMeetingServiceListener.UVCCameraStatus?
    ) {
        // No-op
    }

    override fun onFocusModeStateChanged(p0: Boolean) {
        // No-op
    }

    override fun onFocusModeShareTypeChanged(p0: MobileRTCFocusModeShareType?) {
        // No-op
    }

    override fun onVideoAlphaChannelStatusChanged(p0: Boolean) {
        // No-op
    }

    override fun onAllowParticipantsRequestCloudRecording(p0: Boolean) {
        // No-op
    }

    override fun onSinkJoin3rdPartyTelephonyAudio(p0: String?) {
        // No-op
    }

    override fun onUserConfirmToStartArchive(p0: IMeetingArchiveConfirmHandler?) {
        // No-op
    }

    override fun onCameraControlRequestReceived(
        p0: Long,
        p1: CameraControlRequestType?,
        p2: ICameraControlRequestHandler?
    ) {
        // No-op
    }

    override fun onCameraControlRequestResult(p0: Long, p1: Boolean) {
        // No-op
    }

    override fun onCameraControlRequestResult(p0: Long, p1: CameraControlRequestResult?) {
        // No-op
    }

    override fun onFileSendStart(p0: ZoomSDKFileSender?) {
        // No-op
    }

    override fun onFileReceived(p0: ZoomSDKFileReceiver?) {
        // No-op
    }

    override fun onFileTransferProgress(p0: ZoomSDKFileTransferInfo?) {
        // No-op
    }

    override fun onMuteOnEntryStatusChange(p0: Boolean) {
        // No-op
    }

    override fun onMeetingTopicChanged(p0: String?) {
        // No-op
    }

    override fun onMeetingFullToWatchLiveStream(p0: String?) {
        // No-op
    }

}