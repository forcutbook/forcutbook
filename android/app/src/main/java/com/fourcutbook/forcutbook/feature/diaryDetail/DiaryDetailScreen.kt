package com.fourcutbook.forcutbook.feature.diaryDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fourcutbook.forcutbook.feature.diaryRegistration.DiaryContents
import com.fourcutbook.forcutbook.feature.diaryRegistration.DiaryImage
import com.fourcutbook.forcutbook.feature.diaryRegistration.DiaryTitle
import com.fourcutbook.forcutbook.feature.home.HomeUiState
import com.fourcutbook.forcutbook.feature.home.HomeViewModel

@Composable
fun DiaryDetailRoute(diaryViewModel: HomeViewModel) {
    val uiState by diaryViewModel.uiState.collectAsStateWithLifecycle()

    DiaryDetailScreen(uiState)
}

@Composable
fun DiaryDetailScreen(uiState: HomeUiState) {
    when (uiState) {
        is HomeUiState.DiaryDetail -> {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                DiaryTitle(title = uiState.diary.title)
                DiaryContents(contents = uiState.diary.contents)
                DiaryImage(imageUrl = uiState.diary.imageUrl)
            }
        }

        else -> {}
    }
}
