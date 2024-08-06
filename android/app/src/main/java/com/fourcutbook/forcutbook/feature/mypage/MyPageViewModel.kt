package com.fourcutbook.forcutbook.feature.mypage

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
class MyPageViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<MyPageUiState> =
        MutableStateFlow(MyPageUiState.Default)
    val uiState: StateFlow<MyPageUiState>
        get() = _uiState.asStateFlow()

    private fun fetchUserDiaries(): Flow<List<Diary>> =
        flow { emit(diaryRepository.fetchDiaries()) }

    private fun fetchUserSubscribingCount(): Flow<SubscribingCount> =
        flow { emit(userInfoRepository.fetchSubscribingCount()) }

    init {
        fetchUserInfo()
    }

    fun fetchUserInfo() {
        viewModelScope.launch {
            // todo: query문에 해당 userId만 넣어서 해당 유저의 다이어리들만 뽑아올 수 있도록 만들기
            fetchUserDiaries().zip(fetchUserSubscribingCount()) { diaries, subscribingCount ->
                UserInfo(
                    diaries = diaries,
                    subscribingCount = SubscribingCount(
                        subscribingDiaryCount = subscribingCount.subscribingDiaryCount,
                        subscribingUserCount = subscribingCount.subscribingUserCount
                    )
                )
            }.collect { userInfo ->
                _uiState.value = MyPageUiState.MyInfo(info = userInfo)
            }
        }
    }
}
