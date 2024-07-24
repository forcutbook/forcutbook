package com.fourcutbook.forcutbook.feature.home

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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState.Default(listOf())
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<HomeEvent> = MutableSharedFlow()
    val event: SharedFlow<HomeEvent>
        get() = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            flow {
                emit(diaryRepository.fetchDiaries())
            }.catch {
                _event.emit(HomeEvent.Error)
            }.collect { diaries ->
                _uiState.value = HomeUiState.Default(diaries)
            }
        }
    }

    fun fetchDiaryDetails(diaryId: Long) {
        viewModelScope.launch {
            flow {
                emit(diaryRepository)
            }.onStart {
                _uiState.value = HomeUiState.Loading
            }.catch {
                _event.emit(HomeEvent.Error)
            }.collect {
                _uiState.value = HomeUiState.DiaryDetails
            }
        }
    }

    fun tryDiaryRegistration() {
        _uiState.value = HomeUiState.DiaryRegistration
    }
}
