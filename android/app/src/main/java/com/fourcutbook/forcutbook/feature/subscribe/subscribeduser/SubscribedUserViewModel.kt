package com.fourcutbook.forcutbook.feature.subscribe.subscribeduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribedUserViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SubscribedUserUiState> =
        MutableStateFlow(SubscribedUserUiState.Default)
    val uiState: StateFlow<SubscribedUserUiState>
        get() = _uiState.asStateFlow()

    init {
        fetchSubscribingDiaries()
    }

    fun fetchSubscribingDiaries(userId: Long = 1) {
        viewModelScope.launch {
            flow {
                emit(userInfoRepository.fetchSubscribingDiaries(userId))
            }.collect { userProfiles ->
                _uiState.value = SubscribedUserUiState.SubscribedUsers(userProfiles)
            }
        }
    }
}
