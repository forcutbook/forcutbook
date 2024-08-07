package com.fourcutbook.forcutbook.feature.diaryfeed

import com.fourcutbook.forcutbook.domain.Diary

sealed interface DiaryFeedUiState {

    data class Feed(private val _diaries: List<Diary>) : DiaryFeedUiState {
        val diaries = _diaries.sortedByDescending { it.date }
    }

    data object Loading : DiaryFeedUiState

    data class DiaryDetail(val diaryId: Long) : DiaryFeedUiState
}
