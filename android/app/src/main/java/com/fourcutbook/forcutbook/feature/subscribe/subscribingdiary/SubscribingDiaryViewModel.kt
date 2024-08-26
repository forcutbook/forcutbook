package com.fourcutbook.forcutbook.feature.subscribe.subscribingdiary

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
class SubscribingDiaryViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SubscribingDiaryUiState> =
        MutableStateFlow(SubscribingDiaryUiState.Default)
    val uiState: StateFlow<SubscribingDiaryUiState>
        get() = _uiState.asStateFlow()

    init {
        fetchFollowings()
    }

    fun fetchFollowings(userId: Long? = null) {
        viewModelScope.launch {
            flow {
                emit(userRepository.fetchFollowings(userId))
            }.collect { userProfiles ->
                _uiState.value = SubscribingDiaryUiState.SubscribingDiaries(userProfiles)
            }
        }
    }
}
