package com.fourcutbook.forcutbook.feature.subscribe.subscribeduser

import com.fourcutbook.forcutbook.domain.UserProfile

sealed interface SubscribedUserUiState {

    data object Default : SubscribedUserUiState

    data class SubscribedUsers(
        val value: List<UserProfile>
    ) : SubscribedUserUiState
}
