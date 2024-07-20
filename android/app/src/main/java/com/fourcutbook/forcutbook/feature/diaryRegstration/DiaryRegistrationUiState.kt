package com.fourcutbook.forcutbook.feature.diaryRegstration

import com.fourcutbook.forcutbook.domain.Diary

sealed interface DiaryRegistrationUiState {

    data object Entering : DiaryRegistrationUiState

    data class Created(val diary: Diary) : DiaryRegistrationUiState

    data object Loading : DiaryRegistrationUiState

    data object Done : DiaryRegistrationUiState
}
