package com.fourcutbook.forcutbook.feature.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.NotificationRepository
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
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<NotificationUiState> = MutableStateFlow(
        NotificationUiState(friendRequestNotifications = listOf())
    )
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<NotificationEvent> = MutableSharedFlow()
    val event: SharedFlow<NotificationEvent>
        get() = _event.asSharedFlow()

    init {
        fetchFriendRequestNotifications()
    }

    private fun fetchFriendRequestNotifications() {
        viewModelScope.launch {
            flow {
                emit(notificationRepository.fetchFriendRequestNotification())
            }.collect { notifications ->
                _uiState.value = NotificationUiState(notifications)
            }
        }
    }

    fun postAcceptFollowingRequest(userId: Long) {
        viewModelScope.launch {
            flow {
                emit(userRepository.postAcceptFollowingRequest(userId))
            }.catch {
                _event.emit(NotificationEvent.Error)
            }.collect {
                fetchFriendRequestNotifications()
                _event.emit(NotificationEvent.Accepted)
            }
        }
    }

    fun postDenyFollowingRequest(userId: Long) {
        viewModelScope.launch {
            flow {
                emit(userRepository.postDenyFollowingRequest(userId))
            }.catch {
                _event.emit(NotificationEvent.Error)
            }.collect {
                fetchFriendRequestNotifications()
                _event.emit(NotificationEvent.Denied)
            }
        }
    }
}
