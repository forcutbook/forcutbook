package com.fourcutbook.forcutbook.feature.followingAndFollower.followingList

sealed interface FollowingListEvent {

    data object FollowingCanceled : FollowingListEvent

    data object Error : FollowingListEvent
}
