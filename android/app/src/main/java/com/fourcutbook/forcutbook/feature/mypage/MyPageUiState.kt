package com.fourcutbook.forcutbook.feature.mypage

import com.fourcutbook.forcutbook.domain.UserInfo

sealed interface MyPageUiState {

    data object Default : MyPageUiState

    data class MyInfo(val info: UserInfo) : MyPageUiState
}
