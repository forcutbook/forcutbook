package com.fourcutbook.forcutbook.feature.subscribingdiary

import com.fourcutbook.forcutbook.domain.UserProfile

sealed interface SubscribingDiaryUiState {

    data object Default : SubscribingDiaryUiState

    data class SubscribingDiaries(
        val subscribingDiaries: List<UserProfile>
    ) : SubscribingDiaryUiState
}
