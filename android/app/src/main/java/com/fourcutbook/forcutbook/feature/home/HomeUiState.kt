package com.fourcutbook.forcutbook.feature.home

import com.fourcutbook.forcutbook.domain.Diary

sealed interface HomeUiState {

    data class Default(private val _diaries: List<Diary>) : HomeUiState {
        val diaries = _diaries.sortedByDescending { it.date }
    }
    /*
    todo: 현재 발생하는 문제 -> viewModel을 공유함으로써 일기 상세 화면을 조회할 때 추가적인 요청을 보내지 않고
          uiState를 변경하여 일기의 상세화면을 조회하려고 했음 그러다보니 uiState가 바뀌지 않아 또다시 화면으로 넘어가고
          또다서 화면으로 넘어가는 문제가 발생했다!!!
     */

    data object DiaryRegistration : HomeUiState

    data object Loading : HomeUiState

    data class DiaryDetail(val diary: Diary) : HomeUiState
}
