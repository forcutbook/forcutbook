package com.fourcutbook.forcutbook.feature.userPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.DiaryRepository
import com.fourcutbook.forcutbook.data.repository.UserRepository
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.domain.FollowingStatus
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
class UserPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UserPageUiState> =
        MutableStateFlow(UserPageUiState.UnLoaded)
    val uiState: StateFlow<UserPageUiState>
        get() = _uiState.asStateFlow()

    private fun fetchUserDiaries(userId: Long): Flow<List<Diary>> =
        flow { emit(diaryRepository.fetchUserDiaries(userId)) }

    private fun fetchUserStats(userId: Long): Flow<UserStats> =
        flow { emit(userRepository.fetchUserStats(userId)) }

    fun fetchUserStatsAndDiaries(userId: Long) {
        viewModelScope.launch {
            fetchUserDiaries(userId)
                .onStart {
                    fetchUserStats(userId).collect { userStats ->
                        _uiState.value = UserPageUiState.UserStatsLoaded.Loading(userStats)
                    }
                }.catch {
                    _uiState.value = UserPageUiState.UserStatsLoaded.NotSubscribed(
                        userStats = (uiState.value as UserPageUiState.UserStatsLoaded).userStats
                    )
                }.collect { diaries ->
                    _uiState.value = UserPageUiState.UserStatsLoaded.Subscribed(
                        userStats = (_uiState.value as UserPageUiState.UserStatsLoaded).userStats,
                        _diaries = diaries
                    )
                }
        }
    }

    fun postFollowingRequest(userId: Long) {
        viewModelScope.launch {
            flow {
                emit(userRepository.postFollowingRequest(userId))
            }.collect {
                _uiState.value = UserPageUiState.UserStatsLoaded.NotSubscribed(
                    userStats = (_uiState.value as UserPageUiState.UserStatsLoaded).userStats.copy(
                        followingStatus = FollowingStatus.REQUESTED
                    )
                )
            }
        }
    }

    fun deleteFollowingRequest(userId: Long) {
        viewModelScope.launch {
            flow {
                emit(userRepository.deleteFollowingRequest(userId))
            }.collect {
                _uiState.value = UserPageUiState.UserStatsLoaded.NotSubscribed(
                    userStats = (_uiState.value as UserPageUiState.UserStatsLoaded).userStats.copy(
                        followingStatus = FollowingStatus.NONE
                    )
                )
            }
        }
    }
}
