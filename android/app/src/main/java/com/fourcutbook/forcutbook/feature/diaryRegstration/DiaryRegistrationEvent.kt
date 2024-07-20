package com.fourcutbook.forcutbook.feature.diaryRegstration

sealed interface DiaryRegistrationEvent {

    data object Loading : DiaryRegistrationEvent

    data object Done : DiaryRegistrationEvent
}
