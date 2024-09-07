package com.fourcutbook.forcutbook.feature.diaryfeed

import com.fourcutbook.forcutbook.domain.Diary

sealed interface DiaryFeedUiState {

    data class DiaryFeed(
        private val _diaries: List<Diary>,
        val isNotificationExist: Boolean
    ) : DiaryFeedUiState {
        val diaries = _diaries.sortedByDescending { it.date }
    }

    data object Loading : DiaryFeedUiState
}
