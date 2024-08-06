package com.fourcutbook.forcutbook.feature.diaryDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryDetailViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DiaryDetailUiState> =
        MutableStateFlow(DiaryDetailUiState.Default)
    val uiState: StateFlow<DiaryDetailUiState>
        get() = _uiState.asStateFlow()

    fun fetchDiaryDetail(diaryId: Long) {
        viewModelScope.launch {
            flow {
                emit(diaryRepository.fetchDiaryDetails(diaryId))
            }.collect { diary ->
                _uiState.value = DiaryDetailUiState.DiaryDetail(diary)
            }
        }
    }
}
