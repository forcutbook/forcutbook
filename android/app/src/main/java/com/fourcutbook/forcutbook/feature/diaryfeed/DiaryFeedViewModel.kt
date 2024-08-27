package com.fourcutbook.forcutbook.feature.diaryfeed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.DiaryRepository
import com.fourcutbook.forcutbook.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryFeedViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DiaryFeedUiState> = MutableStateFlow(
        DiaryFeedUiState.Feed(listOf(), false)
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
                emit(diaryRepository.fetchMyDiaries())
            }.zip(flowOf(notificationRepository.fetchFriendRequestNotification())) { diaries, friendRequestNotifications ->
                DiaryFeedUiState.Feed(
                    _diaries = diaries,
                    isNotificationExist = friendRequestNotifications.isNotEmpty()
                )
            }.collect { uiState ->
                Log.d("woogi", "fetchDiaries: ${uiState.isNotificationExist}")
                _uiState.value = uiState
            }
        }
    }

    fun fetchDiaryDetail(diaryId: Long) {
        _uiState.value = DiaryFeedUiState.DiaryDetail(diaryId)
    }
}
