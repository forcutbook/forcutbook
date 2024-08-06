package com.fourcutbook.forcutbook.feature.diaryfeed

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.util.DiaryFixture
import java.time.format.DateTimeFormatter

@Composable
fun DiaryFeedRoute(
    diaryFeedViewModel: DiaryFeedViewModel = hiltViewModel(),
    navigateToDiaryDetail: (diaryId: Long) -> Unit = {}
) {
    val uiState by diaryFeedViewModel.uiState.collectAsStateWithLifecycle()
    // navigateToDiaryDetail을 remember로 감싸서 메모이제이션
    val onDiaryClick: (Long) -> Unit = remember(navigateToDiaryDetail) {
        { diaryId ->
            navigateToDiaryDetail(diaryId)
        }
    }

    DiaryFeedScreen(
        uiState = uiState,
        // todo: recomposition을 어떻게 막아야할까?
        onDiaryClick = onDiaryClick
    )
}

@Composable
fun DiaryFeedScreen(
    modifier: Modifier = Modifier,
    uiState: DiaryFeedUiState,
    onDiaryClick: (diaryId: Long) -> Unit = {}
) {
    Log.d("woogi", "DiaryFeedScreen")
    when (uiState) {
        is DiaryFeedUiState.Feed -> {
            DiariesColumn(
                diaries = uiState.diaries,
                onDiaryClick = onDiaryClick
            )
        }

        is DiaryFeedUiState.Loading -> {
            // todo: change progress bar visibility
        }

        else -> {
        }
    }
}

@Composable
fun DiariesColumn(
    modifier: Modifier = Modifier,
    diaries: List<Diary>,
    onDiaryClick: (diaryId: Long) -> Unit
) {
    Log.d("woogi", "DiariesColumn: $diaries")
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = FcbTheme.padding.basicVerticalPadding)
    ) {
        itemsIndexed(diaries) { index, diary ->
            DiaryItem(
                diary = diary,
                onClick = onDiaryClick
            )
            if (index != diaries.lastIndex) {
                Spacer(
                    modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = FcbTheme.colors.fcbLightBeige)
                )
            }
        }
    }
}

@Composable
fun DiaryItem(
    modifier: Modifier = Modifier,
    diary: Diary,
    onClick: (diaryId: Long) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = FcbTheme.colors.fcbWhite,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(12.dp)
            .clickable { onClick(diary.id) },
        verticalAlignment = Alignment.CenterVertically

    ) {
        // todo: difference between image, icon,....
        Image(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .size(58.dp),
            // todo: 실제 게시물 이미지로 변경
            painter = painterResource(id = R.drawable.demo_image),
            contentDescription = null
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Row {
                Text(
                    style = FcbTheme.typography.footer,
                    text = diary.date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                )
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    style = FcbTheme.typography.footer,
                    text = "woogie" // todo: 닉네임 추가
                )
            }
            Text(
                modifier = Modifier.padding(
                    top = 16.dp
                ),
                style = FcbTheme.typography.body,
                text = diary.title
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun DiaryFeedPreview() {
    DiaryFeedScreen(
        uiState = DiaryFeedUiState.Feed(DiaryFixture.get())
    )
}

@Preview()
@Composable
fun DiaryItemPreview() {
    DiaryItem(diary = DiaryFixture.get().first())
}
