package com.fourcutbook.forcutbook.feature.diaryDetail

import com.fourcutbook.forcutbook.domain.Diary

sealed interface DiaryDetailUiState {

    data object Default : DiaryDetailUiState

    data class DiaryDetail(val diary: Diary) : DiaryDetailUiState
}