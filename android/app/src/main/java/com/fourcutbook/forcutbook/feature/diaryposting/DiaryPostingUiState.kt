package com.fourcutbook.forcutbook.feature.diaryposting

import com.fourcutbook.forcutbook.domain.Diary

sealed interface DiaryPostingUiState {

    data object ImageUploading : DiaryPostingUiState

    data object LoadingForUploading : DiaryPostingUiState

    data class ImageUploaded(val diary: Diary) : DiaryPostingUiState

    data object LoadingForRegistering : DiaryPostingUiState

    data object Registered : DiaryPostingUiState
}