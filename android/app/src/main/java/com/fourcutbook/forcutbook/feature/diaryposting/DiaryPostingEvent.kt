package com.fourcutbook.forcutbook.feature.diaryposting

sealed interface DiaryPostingEvent {

    data object Loading : DiaryPostingEvent

    data object ImageUploaded : DiaryPostingEvent
}
