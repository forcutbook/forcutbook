package com.fourcutbook.forcutbook.feature.diaryfeed

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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryFeedViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DiaryFeedUiState> = MutableStateFlow(
        DiaryFeedUiState.Feed(listOf())
    )
    val uiState: StateFlow<DiaryFeedUiState> = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<DiaryFeedEvent> = MutableSharedFlow()
    val event: SharedFlow<DiaryFeedEvent>
        get() = _event.asSharedFlow()

    init {
        fetchDiaries()
    }

    fun fetchDiaries() {
        viewModelScope.launch {
            flow {
                emit(diaryRepository.fetchDiaries())
            }.catch {
                _event.emit(DiaryFeedEvent.Error)
            }.collect { diaries ->
                _uiState.value = DiaryFeedUiState.Feed(diaries)
            }
        }
    }
}
