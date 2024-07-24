package com.fourcutbook.forcutbook.feature.imageUploading

import com.fourcutbook.forcutbook.domain.Diary

sealed interface ImageUploadingUiState {

    data object Default : ImageUploadingUiState

    data object Loading : ImageUploadingUiState

    data class Uploaded(val diary: Diary) : ImageUploadingUiState

    data object Done : ImageUploadingUiState
}
