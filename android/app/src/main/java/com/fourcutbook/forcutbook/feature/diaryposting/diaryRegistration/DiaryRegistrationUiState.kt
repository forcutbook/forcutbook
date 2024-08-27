package com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration

import com.fourcutbook.forcutbook.domain.Diary

sealed interface DiaryRegistrationUiState {

    data object Default : DiaryRegistrationUiState

    data class ReadyForRegistry(val diary: Diary) : DiaryRegistrationUiState

    data object Loading : DiaryRegistrationUiState
}
