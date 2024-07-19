package com.fourcutbook.forcutbook.feature.login

sealed interface LoginEvent {

    // todo: data object

    data object Loading : LoginEvent

    data object Done : LoginEvent
}
