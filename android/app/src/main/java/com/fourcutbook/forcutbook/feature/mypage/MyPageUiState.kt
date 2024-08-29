package com.fourcutbook.forcutbook.feature.mypage

import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.domain.UserStats

sealed interface MyPageUiState {

    data object Empty : MyPageUiState

    data class MyPage(
        val userStats: UserStats,
        private val _diaries: List<Diary>
    ) : MyPageUiState {
        val diaries = _diaries.sortedByDescending { it.date }
    }

    data object Loading : MyPageUiState
}
