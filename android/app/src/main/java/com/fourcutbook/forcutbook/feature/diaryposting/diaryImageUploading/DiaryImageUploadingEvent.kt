package com.fourcutbook.forcutbook.feature.diaryposting.diaryImageUploading

sealed interface DiaryImageUploadingEvent {

    data object Loading : DiaryImageUploadingEvent

    data class ImageUploaded(
        val filePath: String,
        val title: String,
        val contents: String
    ) : DiaryImageUploadingEvent
}
