package com.fourcutbook.forcutbook.feature.diaryImageUploading

import com.fourcutbook.forcutbook.domain.Diary

sealed interface DiaryImageUploadingUiState {

    data object Default : DiaryImageUploadingUiState

    data object Loading : DiaryImageUploadingUiState

    data class Uploaded(val diary: Diary) : DiaryImageUploadingUiState

    data object Registered : DiaryImageUploadingUiState
}
