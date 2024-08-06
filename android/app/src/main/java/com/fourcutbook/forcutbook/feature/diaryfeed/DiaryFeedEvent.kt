package com.fourcutbook.forcutbook.feature.diaryfeed

sealed interface DiaryFeedEvent {

    data object Loading : DiaryFeedEvent

    data object Error : DiaryFeedEvent

    data object Na
}
