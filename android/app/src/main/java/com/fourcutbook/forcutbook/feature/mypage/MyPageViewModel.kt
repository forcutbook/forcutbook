package com.fourcutbook.forcutbook.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.DiaryRepository
import com.fourcutbook.forcutbook.data.repository.UserRepository
import com.fourcutbook.forcutbook.feature.diaryfeed.DiaryFeedEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<MyPageUiState> = MutableStateFlow(
        MyPageUiState.Empty
    )
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<DiaryFeedEvent> = MutableSharedFlow()
    val event: SharedFlow<DiaryFeedEvent>
        get() = _event.asSharedFlow()

    init {
        fetchMyDiaries()
    }

    fun fetchMyDiaries() {
        viewModelScope.launch {
            flow {
                emit(diaryRepository.fetchMyDiaries())
            }.onStart {
                _uiState.value = MyPageUiState.Loading
            }.zip(flowOf(userRepository.fetchUserStats())) { myDiaries, userStats ->
                MyPageUiState.MyPage(
                    userStats = userStats,
                    _diaries = myDiaries
                )
            }.collect { uiState ->
                _uiState.value = uiState
            }
        }
    }
}
