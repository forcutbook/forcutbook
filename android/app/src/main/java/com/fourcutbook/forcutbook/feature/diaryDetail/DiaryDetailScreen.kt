package com.fourcutbook.forcutbook.feature.diaryDetail

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration.DiaryContents
import com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration.DiaryImage
import com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration.DiaryTitle
import com.fourcutbook.forcutbook.feature.home.HomeUiState
import com.fourcutbook.forcutbook.feature.home.HomeViewModel

@Composable
fun DiaryDetailRoute(
    diaryViewModel: HomeViewModel,
    onBackPressed: () -> Unit = {}
) {
    val uiState by diaryViewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        Log.d("woogi", "DiaryDetailRoute: 뒤로가기")
        onBackPressed()
    }
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
