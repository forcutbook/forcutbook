package com.fourcutbook.forcutbook.feature.diaryDetail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration.DiaryContents
import com.fourcutbook.forcutbook.feature.diaryposting.diaryRegistration.DiaryImage

@Composable
fun DiaryDetailRoute(
    diaryDetailViewModel: DiaryDetailViewModel = hiltViewModel(),
    diaryId: Long?,
    onBackPressed: () -> Unit = {}
) {
    val uiState by diaryDetailViewModel.uiState.collectAsStateWithLifecycle()

    diaryId?.let { id ->
        diaryDetailViewModel.fetchDiaryDetail(id)
    }
    DiaryDetailScreen(
        uiState = uiState,
        onBackClick = onBackPressed
    )
}

@Composable
fun DiaryDetailScreen(
    uiState: DiaryDetailUiState,
    onBackClick: () -> Unit
) {
    Log.d("woogi", "DiaryDetailScreen")
    when (uiState) {
        is DiaryDetailUiState.DiaryDetail -> {
            Column(
                modifier = Modifier
                    .padding(
                        top = FcbTheme.padding.basicVerticalPadding,
                        start = FcbTheme.padding.basicHorizontalPadding,
                        end = FcbTheme.padding.basicHorizontalPadding
                    )
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                DiaryDetailTopAppBar(
                    title = uiState.diary.title,
                    onBackClick = onBackClick
                )
                DiaryContents(contents = uiState.diary.contents)
                DiaryImage(imageUrl = uiState.diary.imageUrl)
            }
        }

        else -> {}
    }
}

@Composable
fun DiaryDetailTopAppBar(
    title: String,
    onBackClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            modifier = Modifier.size(20.dp),
            onClick = onBackClick
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = FcbTheme.padding.basicHorizontalPadding),
            style = FcbTheme.typography.heading,
            // todo: 일관성 없는 코드.. 어느 곳에서는 Scaffold로 처리하고...
            text = title
        )
    }
}
