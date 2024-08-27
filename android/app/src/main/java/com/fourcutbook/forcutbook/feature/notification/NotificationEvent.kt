package com.fourcutbook.forcutbook.feature.notification

sealed interface NotificationEvent {

    data object Accepted : NotificationEvent

    data object Denied : NotificationEvent

    data object Error : NotificationEvent
}
