package com.fourcutbook.forcutbook.feature.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<NotificationUiState> = MutableStateFlow(
        NotificationUiState(friendRequestNotifications = listOf())
    )
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val notifications = notificationRepository.fetchFriendRequestNotification()

            _uiState.value = NotificationUiState(notifications)
        }
    }
}
