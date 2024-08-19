package com.fourcutbook.forcutbook.feature.main

sealed interface MainUiState {

    data object SignedIn : MainUiState

    data object NotSignedIn : MainUiState
}
