package com.fourcutbook.forcutbook.feature.followingAndFollower.followingList

import com.fourcutbook.forcutbook.domain.UserProfile

sealed interface FollowingListUiState {

    data object Default : FollowingListUiState

    data class FollowingList(
        val value: List<UserProfile>
    ) : FollowingListUiState
}
