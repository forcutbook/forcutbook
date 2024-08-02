package com.fourcutbook.forcutbook.feature.diaryImageUploading

sealed interface DiaryImageUploadingEvent {

    data object Loading : DiaryImageUploadingEvent

    data object Done : DiaryImageUploadingEvent
}
