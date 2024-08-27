package com.fourcutbook.forcutbook.feature.followingAndFollower.followerList

import com.fourcutbook.forcutbook.domain.UserProfile

sealed interface FollowerListUiState {

    data object Default : FollowerListUiState

    data class SubscribedUsers(
        val value: List<UserProfile>
    ) : FollowerListUiState
}
