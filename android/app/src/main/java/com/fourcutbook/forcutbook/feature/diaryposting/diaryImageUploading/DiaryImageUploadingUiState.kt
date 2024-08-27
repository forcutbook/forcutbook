package com.fourcutbook.forcutbook.feature.diaryposting.diaryImageUploading

import android.graphics.Bitmap

sealed interface DiaryImageUploadingUiState {

    data class ImageSelecting(val bitmap: Bitmap? = null) : DiaryImageUploadingUiState

    data object LoadingForUploading : DiaryImageUploadingUiState

    data object Registered : DiaryImageUploadingUiState
}
