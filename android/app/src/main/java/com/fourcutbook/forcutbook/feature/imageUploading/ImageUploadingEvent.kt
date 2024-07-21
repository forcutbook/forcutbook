package com.fourcutbook.forcutbook.feature.imageUploading

sealed interface ImageUploadingEvent {

    data object Loading : ImageUploadingEvent

    data object Done : ImageUploadingEvent
}
