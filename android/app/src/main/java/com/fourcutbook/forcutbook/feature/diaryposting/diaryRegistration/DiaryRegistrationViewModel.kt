package com.fourcutbook.forcutbook.feature.diaryposting

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.DiaryRepository
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration.DiaryRegistrationEvent
import com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration.DiaryRegistrationUiState
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
import java.io.File
import java.time.LocalDateTime
import javax.inject.Inject

// todo: DiaryViewModel?
@HiltViewModel
class DiaryRegistrationViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DiaryRegistrationUiState> =
        MutableStateFlow(DiaryRegistrationUiState.Default)
    val uiState: StateFlow<DiaryRegistrationUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<DiaryRegistrationEvent> =
        MutableSharedFlow()
    val event: SharedFlow<DiaryRegistrationEvent>
        get() = _event.asSharedFlow()

    fun updateDiary(
        title: String,
        contents: String,
        imageBitmap: Bitmap,
        imageFile: File
    ) {
        _uiState.value = DiaryRegistrationUiState.ReadyForRegistry(
            Diary(
                id = 5474,
                title = title,
                contents = contents,
                date = LocalDateTime.now(),
                image = imageBitmap
            )
        )
    }

    fun postDiary(
        diary: Diary,
        image: File
    ) {
        viewModelScope.launch {
            flow {
                emit(diaryRepository.postDiary(diary = diary, image = image))
            }.onStart {
                _uiState.emit(DiaryRegistrationUiState.Loading)
            }.collect {
                _event.emit(DiaryRegistrationEvent.Registered)
            }
        }
    }
}
