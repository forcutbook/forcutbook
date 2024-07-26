package com.fourcutbook.forcutbook.feature.home

import com.fourcutbook.forcutbook.domain.Diary

sealed interface HomeUiState {

    data class Default(private val _diaries: List<Diary>) : HomeUiState {
        val diaries = _diaries.sortedByDescending { it.date }
    }

    data object DiaryRegistration : HomeUiState

    data object Loading : HomeUiState

    data class DiaryDetail(val diary: Diary) : HomeUiState
}
