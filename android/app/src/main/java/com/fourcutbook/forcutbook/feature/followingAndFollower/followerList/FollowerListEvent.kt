package com.fourcutbook.forcutbook.feature.followingAndFollower.followerList

sealed interface FollowerListEvent {

    data object Error : FollowerListEvent

    data object Canceled : FollowerListEvent
}
