package com.fourcutbook.forcutbook.feature.followingAndFollower.followingList

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
class FollowingListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<FollowingListUiState> =
        MutableStateFlow(FollowingListUiState.Default)
    val uiState: StateFlow<FollowingListUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<FollowingListEvent> =
        MutableSharedFlow()
    val event: SharedFlow<FollowingListEvent>
        get() = _event.asSharedFlow()

    init {
        fetchFollowings()
    }

    fun fetchFollowings(userId: Long? = null) {
        viewModelScope.launch {
            flow {
                emit(userRepository.fetchFollowings(userId))
            }.catch {
                _event.emit(FollowingListEvent.Error)
            }.collect { userProfiles ->
                _uiState.value = FollowingListUiState.FollowingList(userProfiles)
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
                _event.emit(FollowingListEvent.Error)
            }.collect {
                _event.emit(FollowingListEvent.FollowingRequested)
                fetchFollowings(userIdOfPageOwner)
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
                _event.emit(FollowingListEvent.Error)
            }.collect {
                _event.emit(FollowingListEvent.FollowingRequestCanceled)
                fetchFollowings(userIdOfPageOwner)
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
                _event.emit(FollowingListEvent.Error)
            }.collect {
                _event.emit(FollowingListEvent.FollowingCanceled)
                fetchFollowings(userIdOfPageOwner)
            }
        }
    }
}
