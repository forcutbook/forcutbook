package com.fourcutbook.forcutbook.feature.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.DiaryRepository
import com.fourcutbook.forcutbook.data.repository.UserRepository
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.domain.UserStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<MyPageUiState> =
        MutableStateFlow(MyPageUiState.Default)
    val uiState: StateFlow<MyPageUiState>
        get() = _uiState.asStateFlow()

    private fun fetchMyDiaries(): Flow<List<Diary>> =
        flow { emit(diaryRepository.fetchMyDiaries()) }

    private fun fetchUserStats(): Flow<UserStats> =
        flow { emit(userRepository.fetchUserStats()) }

    init {
        fetchUserInfo()
    }

    fun fetchUserInfo() {
        viewModelScope.launch {
            fetchMyDiaries()
                .onStart {
                    fetchUserStats().collect { userStats ->
                        _uiState.value = MyPageUiState.Loading(userStats)
                    }
                }.catch {
                    Log.d("woogi", "fetchUserInfo: $it")
                }.collect { diaries ->
                    Log.d("woogi", "fetchUserInfo: $diaries")
                    _uiState.value = MyPageUiState.MyPage(
                        userStats = (_uiState.value as MyPageUiState.Loading).userStats,
                        diaries = diaries
                    )
                }
        }
    }
}
