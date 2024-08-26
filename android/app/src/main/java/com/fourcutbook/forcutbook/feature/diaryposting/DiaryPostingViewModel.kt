package com.fourcutbook.forcutbook.feature.diaryposting

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.DiaryRepository
import com.fourcutbook.forcutbook.domain.Diary
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
import javax.inject.Inject

// todo: DiaryViewModel?
@HiltViewModel
class DiaryPostingViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DiaryPostingUiState> =
        MutableStateFlow(DiaryPostingUiState.IncludingImage.ImageSelecting())
    val uiState: StateFlow<DiaryPostingUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<DiaryPostingEvent> = MutableSharedFlow()
    val event: SharedFlow<DiaryPostingEvent>
        get() = _event.asSharedFlow()

    fun postImage(
        imageFile: File,
        imageBitmap: Bitmap
    ) {
        viewModelScope.launch {
            flow {
                emit(diaryRepository.postImage(imageFile))
            }.onStart {
                _uiState.emit(DiaryPostingUiState.LoadingForUploading)
            }.collect { diary ->
                _uiState.emit(DiaryPostingUiState.IncludingImage.ImageUploaded(diary.copy(image = imageBitmap)))
            }
        }
    }

    fun selectImage(bitmap: Bitmap) {
        viewModelScope.launch {
            _uiState.value = DiaryPostingUiState.IncludingImage.ImageSelecting(bitmap)
        }
    }

    fun postDiary(
        diary: Diary,
        image: File
    ) {
        viewModelScope.launch {
            flow {
                emit(diaryRepository.postDiary(diary = diary, image = image))
            }.onStart {
                _uiState.emit(DiaryPostingUiState.LoadingForUploading)
            }.collect {
                _uiState.emit(DiaryPostingUiState.Registered)
            }
        }
    }
}
