package com.fourcutbook.forcutbook.feature.followingAndFollower.followerList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<FollowerListUiState> =
        MutableStateFlow(FollowerListUiState.Default)
    val uiState: StateFlow<FollowerListUiState>
        get() = _uiState.asStateFlow()

    init {
        fetchSubscribingDiaries()
    }

    fun fetchSubscribingDiaries(userId: Long? = 1) {
        viewModelScope.launch {
            flow {
                emit(userRepository.fetchFollowings(userId))
            }.collect { userProfiles ->
                _uiState.value = FollowerListUiState.SubscribedUsers(userProfiles)
            }
        }
    }
}
