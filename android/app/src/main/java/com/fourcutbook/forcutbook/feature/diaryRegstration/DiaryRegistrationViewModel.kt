package com.fourcutbook.forcutbook.feature.diaryRegstration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.DiaryRepository
import com.fourcutbook.forcutbook.domain.Diary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryRegistrationViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _event: MutableSharedFlow<DiaryRegistrationEvent> = MutableSharedFlow()
    val event: SharedFlow<DiaryRegistrationEvent>
        get() = _event.asSharedFlow()

    suspend fun createAIDiaries(diary: Diary) {
        viewModelScope.launch {
            flow {
                emit(diaryRepository.createAIDiaries(diary))
            }.onStart {
                _event.emit(DiaryRegistrationEvent.Loading)
            }.collect {
                _event.emit(DiaryRegistrationEvent.Done)
            }
        }
    }
}
