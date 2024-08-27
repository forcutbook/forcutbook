package com.fourcutbook.forcutbook.feature.searching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.UserRepository
import com.fourcutbook.forcutbook.data.repository.UserSearchingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSearchingViewModel @Inject constructor(
    private val userSearchingRepository: UserSearchingRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _searchingNickname: MutableStateFlow<String> = MutableStateFlow("")
    val searchingNickname: StateFlow<String>
        get() = _searchingNickname.asStateFlow()

    private val _event: MutableSharedFlow<UserSearchingEvent> = MutableSharedFlow()
    val event: SharedFlow<UserSearchingEvent>
        get() = _event.asSharedFlow()

    /**
     * for debouncing
     */
    val uiState: StateFlow<UserSearchingUiState> = searchingNickname
        .debounce(1000)
        .filter { it.isNotEmpty() }
        .flatMapLatest { nickname ->
            userSearchingRepository
                .fetchUsers(nickname)
                .catch { _event.emit(UserSearchingEvent.Error) }
                .map { userProfiles ->
                    UserSearchingUiState(userProfiles = userProfiles)
                }
        }.stateIn(
            initialValue = UserSearchingUiState(userProfiles = listOf()),
            started = SharingStarted.WhileSubscribed(5000),
            scope = viewModelScope
        )

    fun enterNickname(nickname: String) {
        _searchingNickname.value = nickname
    }

    fun postFollowing(
        userId: Long,
        searchingNickname: String
    ) {
        viewModelScope.launch {
            flow {
                emit(userRepository.postFollowingRequest(userId))
            }.collect {
                /* todo: for refetching userProfiles. need to improve */
                _searchingNickname.value = ""
                _searchingNickname.value = searchingNickname
                _event.emit(UserSearchingEvent.FollowingRequest)
            }
        }
    }

    fun postFollowingRequestCancel(
        userId: Long,
        searchingNickname: String
    ) {
        viewModelScope.launch {
            flow {
                emit(userRepository.deleteFollowingRequest(userId))
            }.collect {
                /* todo: for refetching userProfiles. need to improve */
                _searchingNickname.value = ""
                _searchingNickname.value = searchingNickname
                _event.emit(UserSearchingEvent.FollowingRequestCancel)
            }
        }
    }
}
