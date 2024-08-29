package com.fourcutbook.forcutbook.feature.followingAndFollower.followingList

sealed interface FollowingListEvent {

    data object FollowingCanceled : FollowingListEvent

    data object Error : FollowingListEvent

    data object FollowingRequested : FollowingListEvent

    data object FollowingRequestCanceled : FollowingListEvent
}
