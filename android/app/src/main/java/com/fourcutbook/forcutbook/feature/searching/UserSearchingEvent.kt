package com.fourcutbook.forcutbook.feature.searching

sealed interface UserSearchingEvent {

    data object FollowingRequest : UserSearchingEvent

    data object FollowingRequestCancel : UserSearchingEvent

    data object Error : UserSearchingEvent
}
