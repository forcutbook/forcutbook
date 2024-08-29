package com.fourcutbook.forcutbook.feature.followingAndFollower.followerList

sealed interface FollowerListEvent {

    data object Error : FollowerListEvent

    data object Canceled : FollowerListEvent

    data object FollowingRequested : FollowerListEvent

    data object FollowingRequestCanceled : FollowerListEvent

    data object FollowingCanceled : FollowerListEvent
}
