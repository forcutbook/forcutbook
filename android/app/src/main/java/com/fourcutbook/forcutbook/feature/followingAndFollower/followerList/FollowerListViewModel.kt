package com.fourcutbook.forcutbook.feature.followingAndFollower.followerList

import android.util.Log
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
        MutableStateFlow(FollowerListUiState.Empty)
    val uiState: StateFlow<FollowerListUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<FollowerListEvent> =
        MutableSharedFlow()
    val event: SharedFlow<FollowerListEvent>
        get() = _event.asSharedFlow()

    init {
        fetchFollowers()
    }

    /**
     * null means fetch my followers
     */
    fun fetchFollowers(userId: Long? = null) {
        viewModelScope.launch {
            flow {
                emit(userRepository.fetchFollowers(userId))
            }.catch {
                _event.emit(FollowerListEvent.Error)
            }.collect { userProfiles ->
                Log.d("woogi", "fetchFollowers: $userId")
                userId?.let {
                    _uiState.value = FollowerListUiState.FollowerList.Other(userProfiles)
                } ?: run {
                    _uiState.value = FollowerListUiState.FollowerList.MyFollower(userProfiles)
                }
            }
        }
    }

    /**
     * userIdOfFollowing: user id that you request following
     * userIdOfUserPage: user id of owner of follower list page you are seeing. null means my page
     */
    fun postFollowingRequest(
        userIdOfFollowing: Long,
        userIdOfPageOwner: Long? = null
    ) {
        viewModelScope.launch {
            flow {
                emit(userRepository.postFollowingRequest(userIdOfFollowing))
            }.catch {
                _event.emit(FollowerListEvent.Error)
            }.collect {
                _event.emit(FollowerListEvent.FollowingRequested)
                fetchFollowers(userIdOfPageOwner)
            }
        }
    }

    fun postFollowingRequestCancel(
        userIdOfFollowing: Long,
        userIdOfPageOwner: Long? = null
    ) {
        viewModelScope.launch {
            flow {
                emit(userRepository.deleteFollowingRequest(userIdOfFollowing))
            }.catch {
                _event.emit(FollowerListEvent.Error)
            }.collect {
                _event.emit(FollowerListEvent.FollowingRequested)
                fetchFollowers(userIdOfPageOwner)
            }
        }
    }

    fun deleteFollowing(
        userIdOfFollowing: Long,
        userIdOfPageOwner: Long? = null
    ) {
        viewModelScope.launch {
            flow {
                emit(userRepository.deleteFollowing(userIdOfFollowing))
            }.catch {
                _event.emit(FollowerListEvent.Error)
            }.collect {
                _event.emit(FollowerListEvent.FollowingCanceled)
                fetchFollowers(userIdOfPageOwner)
            }
        }
    }

    fun deleteFollower(friendId: Long) {
        viewModelScope.launch {
            flow {
                emit(userRepository.deleteFollower(friendId))
            }.catch {
                _event.emit(FollowerListEvent.Error)
            }.collect {
                _event.emit(FollowerListEvent.Canceled)
                fetchFollowers()
            }
        }
    }
}
