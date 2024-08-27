package com.fourcutbook.forcutbook.feature.userPage

import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.domain.UserStats

sealed interface UserPageUiState {

    data object UnLoaded : UserPageUiState

    sealed interface UserStatsLoaded : UserPageUiState {

        val userStats: UserStats

        data class Loading(override val userStats: UserStats) : UserStatsLoaded

        data class NotSubscribed(override val userStats: UserStats) : UserStatsLoaded

        data class Subscribed(
            override val userStats: UserStats,
            private val _diaries: List<Diary>
        ) : UserStatsLoaded{
            val diaries = _diaries.sortedByDescending { it.date }
        }
    }
}
