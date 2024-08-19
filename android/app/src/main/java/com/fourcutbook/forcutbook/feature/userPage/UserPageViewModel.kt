package com.fourcutbook.forcutbook.feature.userPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourcutbook.forcutbook.data.repository.DiaryRepository
import com.fourcutbook.forcutbook.data.repository.UserInfoRepository
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.domain.SubscribingCount
import com.fourcutbook.forcutbook.domain.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPageViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UserPageUiState> =
        MutableStateFlow(UserPageUiState.Loading)
    val uiState: StateFlow<UserPageUiState>
        get() = _uiState.asStateFlow()

    private fun fetchUserDiaries(userId: Long): Flow<List<Diary>> =
        flow { emit(diaryRepository.fetchDiaries(userId)) }

    private fun fetchUserSubscribingCount(userId: Long): Flow<SubscribingCount> =
        flow { emit(userInfoRepository.fetchSubscribingCount(userId)) }

    fun fetchUserInfo(userId: Long) {
        viewModelScope.launch {
            // todo: query문에 해당 userId만 넣어서 해당 유저의 다이어리들만 뽑아올 수 있도록 만들기
            fetchUserDiaries(userId).zip(fetchUserSubscribingCount(userId)) { diaries, subscribingCount ->
                UserInfo(
                    diaries = diaries,
                    // todo: 유저 고유 아이디도 받아와야함
                    userId = 1,
                    subscribingCount = SubscribingCount(
                        subscribingDiaryCount = subscribingCount.subscribingDiaryCount,
                        subscribingUserCount = subscribingCount.subscribingUserCount
                    )
                )
            }.collect { userInfo ->
                _uiState.value = UserPageUiState.UserPage(value = userInfo)
            }
        }
    }
}
