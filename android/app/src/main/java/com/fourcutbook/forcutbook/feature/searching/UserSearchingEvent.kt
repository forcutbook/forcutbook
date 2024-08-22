package com.fourcutbook.forcutbook.feature.searching

sealed interface UserSearchingEvent {

    data object Error : UserSearchingEvent
}
