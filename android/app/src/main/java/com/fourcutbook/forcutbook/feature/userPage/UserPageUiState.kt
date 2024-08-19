package com.fourcutbook.forcutbook.feature.userPage

import com.fourcutbook.forcutbook.domain.UserInfo

sealed interface UserPageUiState {

    data object Loading : UserPageUiState

    data class UserPage(val value: UserInfo) : UserPageUiState
}
