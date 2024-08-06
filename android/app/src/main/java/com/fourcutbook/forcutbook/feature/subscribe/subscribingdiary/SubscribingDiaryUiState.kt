package com.fourcutbook.forcutbook.feature.subscribe.subscribingdiary

import com.fourcutbook.forcutbook.domain.UserProfile

sealed interface SubscribingDiaryUiState {

    data object Default : SubscribingDiaryUiState

    data class SubscribingDiaries(
        val value: List<UserProfile>
    ) : SubscribingDiaryUiState
}
