package com.fourcutbook.forcutbook.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToDiaryRegistration: () -> Unit = {},
    navigateToDiaryDetail: () -> Unit = {},
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onDiaryClick = homeViewModel::fetchDiaryDetail,
        navigateToDiaryRegistration = navigateToDiaryRegistration,
        navigateToDiaryDetail = navigateToDiaryDetail
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onDiaryClick: (diaryId: Long) -> Unit = {},
    navigateToDiaryDetail: () -> Unit = {},
    navigateToDiaryRegistration: () -> Unit = {},
) {
    when (uiState) {
        is HomeUiState.Default -> {
            Column(modifier = modifier.fillMaxSize()) {
                HomeDiariesColumn(
                    diaries = uiState.diaries,
                    onDiaryClick = onDiaryClick
                )
            }
        }

        is HomeUiState.Loading -> {
            // todo: change progress bar visibility
        }

        is HomeUiState.DiaryDetail -> navigateToDiaryDetail()

        is HomeUiState.DiaryRegistration -> navigateToDiaryRegistration()
    }
}

@Composable
fun HomeDiariesColumn(
    modifier: Modifier = Modifier,
    diaries: List<Diary>,
    onDiaryClick: (diaryId: Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        itemsIndexed(diaries) { index, diary ->
            HomeDiaryItem(
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
fun HomeDiaryItem(
    modifier: Modifier = Modifier,
    diary: Diary,
    onClick: (diaryId: Long) -> Unit = {},
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
            painter = painterResource(id = R.drawable.demo_image),
            contentDescription = null
        )
        Column {
            Row(
                modifier = Modifier.padding(start = 16.dp)
            ) {
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
                    start = 16.dp,
                    top = 16.dp
                ),
                text = diary.title
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun HomePreview() {
    HomeScreen(uiState = HomeUiState.Default(DiaryFixture.get()))
}

@Preview()
@Composable
fun DiaryPreview() {
    HomeDiaryItem(diary = DiaryFixture.get().first())
}
