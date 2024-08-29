package com.fourcutbook.forcutbook.feature.followingAndFollower.followerList

import com.fourcutbook.forcutbook.domain.UserProfile

sealed interface FollowerListUiState {

    data object Empty : FollowerListUiState

    sealed interface FollowerList : FollowerListUiState {

        val value: List<UserProfile>

        data class MyFollower(override val value: List<UserProfile>) : FollowerList

        data class Other(override val value: List<UserProfile>) : FollowerList
    }
}
