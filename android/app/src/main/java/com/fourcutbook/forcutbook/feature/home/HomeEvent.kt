package com.fourcutbook.forcutbook.feature.home

sealed interface HomeEvent {

    data object DiaryDetails : HomeEvent
}
