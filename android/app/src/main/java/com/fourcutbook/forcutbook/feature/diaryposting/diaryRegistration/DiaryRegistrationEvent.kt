package com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration

sealed interface DiaryRegistrationEvent {

    data object Registered : DiaryRegistrationEvent

    data object Error : DiaryRegistrationEvent
}
