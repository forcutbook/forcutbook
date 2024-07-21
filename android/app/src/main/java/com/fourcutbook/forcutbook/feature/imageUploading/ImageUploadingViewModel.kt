package com.fourcutbook.forcutbook.feature.imageUploading

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
class ImageUploadingViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ImageUploadingUiState> =
        MutableStateFlow(ImageUploadingUiState.Default)
    val uiState: StateFlow<ImageUploadingUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<ImageUploadingEvent> = MutableSharedFlow()
    val event: SharedFlow<ImageUploadingEvent>
        get() = _event.asSharedFlow()

    fun uploadImage(image: Bitmap) {
        viewModelScope.launch {
            flow {
                emit(diaryRepository.postImage(image))
            }.onStart {
                _uiState.emit(ImageUploadingUiState.Loading)
            }.collect { diary ->
                _uiState.emit(ImageUploadingUiState.Uploaded(diary))
            }
        }
    }
}
