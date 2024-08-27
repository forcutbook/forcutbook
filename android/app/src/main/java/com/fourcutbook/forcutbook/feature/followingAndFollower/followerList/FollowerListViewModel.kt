package com.fourcutbook.forcutbook.feature.followingAndFollower.followerList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

    private val _event: MutableSharedFlow<FollowerListEvent> =
        MutableSharedFlow()
    val event: SharedFlow<FollowerListEvent>
        get() = _event.asSharedFlow()

    init {
        fetchFollowers()
    }

    fun fetchFollowers(userId: Long? = null) {
        viewModelScope.launch {
            flow {
                emit(userRepository.fetchFollowers(userId))
            }.catch {
                _event.emit(FollowerListEvent.Error)
            }.collect { userProfiles ->
                _uiState.value = FollowerListUiState.SubscribedUsers(userProfiles)
            }
        }
    }

    fun deleteFollower(friendId: Long) {
        viewModelScope.launch {
            flow {
                emit(userRepository.deleteFollower(friendId))
            }.collect {
                _event.emit(FollowerListEvent.Canceled)
                fetchFollowers()
            }
        }
    }
}
