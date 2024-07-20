package com.fourcutbook.forcutbook.feature.diaryRegstration

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryRegistrationViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DiaryRegistrationUiState> =
        MutableStateFlow(DiaryRegistrationUiState.Entering)
    val uiState: StateFlow<DiaryRegistrationUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<DiaryRegistrationEvent> = MutableSharedFlow()
    val event: SharedFlow<DiaryRegistrationEvent>
        get() = _event.asSharedFlow()

    fun createAIDiaries(image: Bitmap) {
        viewModelScope.launch {
            flow {
                emit(diaryRepository.createAIDiaries(image))
            }.onStart {
                _uiState.emit(DiaryRegistrationUiState.Loading)
            }.collect { diary ->
                _uiState.emit(DiaryRegistrationUiState.Created(diary))
            }
        }
    }
}
