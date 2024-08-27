package com.fourcutbook.forcutbook.feature.diaryposting.diaryImageUploading

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
import java.io.File
import javax.inject.Inject

// todo: DiaryViewModel?
@HiltViewModel
class DiaryImageUploadingViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DiaryImageUploadingUiState> =
        MutableStateFlow(DiaryImageUploadingUiState.ImageSelecting())
    val uiState: StateFlow<DiaryImageUploadingUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<DiaryImageUploadingEvent> = MutableSharedFlow()
    val event: SharedFlow<DiaryImageUploadingEvent>
        get() = _event.asSharedFlow()

    fun postImage(
        imageFile: File,
        imageBitmap: Bitmap
    ) {
        viewModelScope.launch {
            flow {
                emit(diaryRepository.postImage(imageFile))
            }.onStart {
                _uiState.emit(DiaryImageUploadingUiState.LoadingForUploading)
            }.collect { diary ->
                _event.emit(
                    DiaryImageUploadingEvent.ImageUploaded(
                        filePath = imageFile.path,
                        title = diary.title,
                        contents = diary.contents
                    )
                )
                _uiState.emit(DiaryImageUploadingUiState.ImageSelecting(bitmap = imageBitmap))
            }
        }
    }

    fun selectImage(bitmap: Bitmap) {
        viewModelScope.launch {
            _uiState.emit(DiaryImageUploadingUiState.ImageSelecting(bitmap = bitmap))
        }
    }
}
