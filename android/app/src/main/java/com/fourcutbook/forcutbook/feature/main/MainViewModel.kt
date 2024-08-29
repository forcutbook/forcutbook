package com.fourcutbook.forcutbook.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState.NotSignedIn)
    val uiState: StateFlow<MainUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            tokenRepository
                .fetchUserId()
                .collect { userId ->
                    userId?.let {
                        _uiState.value = MainUiState.SignedIn
                    }
                }
        }
    }
}
