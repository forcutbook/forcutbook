package com.fourcutbook.forcutbook.feature.mypage

import com.fourcutbook.forcutbook.domain.UserInfo

sealed interface MyPageUiState {

    data object Default : MyPageUiState

    data class MyPage(val value: UserInfo) : MyPageUiState
}
