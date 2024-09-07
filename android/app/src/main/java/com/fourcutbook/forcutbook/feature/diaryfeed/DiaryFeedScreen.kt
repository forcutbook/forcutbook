package com.fourcutbook.forcutbook.feature.diaryfeed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.domain.Diary
import com.fourcutbook.forcutbook.feature.FcbTopAppBarWithIcon
import com.fourcutbook.forcutbook.util.DiaryFixture
import com.fourcutbook.forcutbook.util.noRippleClickable
import java.time.format.DateTimeFormatter

@Composable
fun DiaryFeedRoute(
    viewModel: DiaryFeedViewModel = hiltViewModel(),
    navigateToDiaryDetail: (diaryId: Long) -> Unit = {},
    navigateToNotification: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DiaryFeedScreen(
        uiState = uiState,
        onDiaryClick = navigateToDiaryDetail,
        onNotificationClick = navigateToNotification,
        onDiariesRefresh = viewModel::fetchDiaries
    )
}

@Composable
fun DiaryFeedScreen(
    modifier: Modifier = Modifier,
    uiState: DiaryFeedUiState,
    onDiaryClick: (diaryId: Long) -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onDiariesRefresh: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    when (uiState) {
        is DiaryFeedUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterHorizontally),
                    color = FcbTheme.colors.fcbDarkBeige02
                )
            }
        }

        is DiaryFeedUiState.DiaryFeed -> {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                FcbTopAppBarWithIcon(
                    title = stringResource(id = R.string.header_of_home_screen),
                    iconResource = if (uiState.isNotificationExist) {
                        painterResource(id = R.drawable.ic_notification_exist)
                    } else {
                        painterResource(id = R.drawable.ic_notification_none)
                    },
                    onIconClick = onNotificationClick
                )
                DiariesColumn(
                    diaries = uiState.diaries,
                    onDiaryClick = onDiaryClick,
                    onDiariesRefresh = onDiariesRefresh
                )
            }
        }

        else -> {
        }
    }
}

@Composable
fun DiariesColumn(
    modifier: Modifier = Modifier,
    diaries: List<Diary>,
    onDiaryClick: (diaryId: Long) -> Unit = {},
    onDiariesRefresh: () -> Unit = {}
) {
    val scrollState = rememberLazyListState()

    if (scrollState.isScrollInProgress && !scrollState.canScrollBackward) {
        onDiariesRefresh()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = FcbTheme.padding.basicVerticalPadding),
        state = scrollState
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
            .noRippleClickable { onClick(diary.diaryId) },
        verticalAlignment = Alignment.CenterVertically

    ) {
        // todo: difference between image, icon,....
        AsyncImage(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .size(58.dp),
            // todo: 실제 게시물 이미지로 변경
            model = diary.imageUrl,
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
                    text = diary.nickname // todo: 닉네임 추가
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
        uiState = DiaryFeedUiState.DiaryFeed(DiaryFixture.get(), true)
    )
}

@Preview()
@Composable
fun DiaryItemPreview() {
    DiaryItem(diary = DiaryFixture.get().first())
}
