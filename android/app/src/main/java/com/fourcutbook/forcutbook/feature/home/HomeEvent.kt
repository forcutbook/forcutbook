package com.fourcutbook.forcutbook.feature.home

sealed interface HomeEvent {

    data object Loading : HomeEvent

    data object Error : HomeEvent
}
