package com.fourcutbook.forcutbook.feature.diaryposting

import android.graphics.Bitmap
import com.fourcutbook.forcutbook.domain.Diary

sealed interface DiaryPostingUiState {

    sealed interface IncludingImage : DiaryPostingUiState {
        val bitmap: Bitmap?

        data class ImageSelecting(override val bitmap: Bitmap? = null) : IncludingImage

        data class ImageUploaded(val diary: Diary) : IncludingImage {
            override val bitmap: Bitmap?
                get() = diary.image
        }
    }

    data object LoadingForUploading : DiaryPostingUiState

    data object LoadingForRegistering : DiaryPostingUiState

    data object Registered : DiaryPostingUiState
}
