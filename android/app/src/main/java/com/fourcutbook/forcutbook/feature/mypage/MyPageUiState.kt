package com.fourcutbook.forcutbook.feature.mypage

import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.domain.UserStats

sealed interface MyPageUiState {

    val userStats: UserStats?

    data object Default : MyPageUiState {
        override val userStats: UserStats? = null
    }

    data class Loading(override val userStats: UserStats) : MyPageUiState

    data class MyPage(
        override val userStats: UserStats,
        val diaries: List<Diary>
    ) : MyPageUiState
}
