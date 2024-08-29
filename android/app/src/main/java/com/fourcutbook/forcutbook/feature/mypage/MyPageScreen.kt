package com.fourcutbook.forcutbook.feature.mypage

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.domain.FollowingStatus
import com.fourcutbook.forcutbook.domain.UserStats
import com.fourcutbook.forcutbook.feature.FcbTopAppBarWithOnlyTitle
import com.fourcutbook.forcutbook.feature.diaryfeed.DiariesColumn
import com.fourcutbook.forcutbook.util.DiaryFixture
import com.fourcutbook.forcutbook.util.noRippleClickable

@Composable
fun MyPageRoute(
    viewModel: MyPageViewModel,
    onSubscribingUserClick: (userId: Long) -> Unit = {},
    onSubscribedUserClick: (userId: Long) -> Unit = {},
    onDiaryClick: (diaryId: Long) -> Unit = {}
) {
    val uiState: MyPageUiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyPageScreen(
        modifier = Modifier,
        uiState = uiState,
        onSubscribingUserClick = onSubscribingUserClick,
        onSubscribedUserClick = onSubscribedUserClick,
        onDiaryClick = onDiaryClick,
        onDiariesRefresh = viewModel::fetchMyDiaries
    )
}

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    uiState: MyPageUiState,
    onSubscribingUserClick: (userId: Long) -> Unit = {},
    onSubscribedUserClick: (userId: Long) -> Unit = {},
    onDiaryClick: (diaryId: Long) -> Unit = {},
    onDiariesRefresh: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = FcbTheme.padding.basicVerticalPadding)
    ) {
        FcbTopAppBarWithOnlyTitle(title = stringResource(id = R.string.header_of_my_page))
        when (uiState) {
            is MyPageUiState.MyPage -> {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = FcbTheme.padding.basicVerticalPadding),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UserPageSubscribingCount(
                        modifier = Modifier.padding(start = FcbTheme.padding.basicHorizontalPadding),
                        typeOfCount = R.string.subscribing_diary_description,
                        count = uiState.userStats.subscribingDiaryCount,
                        onClick = { onSubscribingUserClick(uiState.userStats.userId) }
                    )
                    UserPageSubscribingCount(
                        typeOfCount = R.string.subscribing_user_description,
                        count = uiState.userStats.subscribingUserCount,
                        onClick = { onSubscribedUserClick(uiState.userStats.userId) }
                    )
                    UserPageSubscribingCount(
                        modifier = Modifier.padding(end = FcbTheme.padding.basicHorizontalPadding),
                        typeOfCount = R.string.count_of_diaries,
                        count = uiState.userStats.diaryCount
                    )
                }
                DiariesColumn(
                    diaries = uiState.diaries,
                    onDiaryClick = onDiaryClick,
                    onDiariesRefresh = onDiariesRefresh
                )
            }

            is MyPageUiState.Loading -> {
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

            else -> {
            }
        }
    }
}

@Composable
fun UserPageSubscribingCount(
    modifier: Modifier = Modifier,
    @StringRes typeOfCount: Int,
    count: Int,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.noRippleClickable { onClick() },
            fontSize = 14.sp,
            style = FcbTheme.typography.heading,
            text = stringResource(typeOfCount)
        )
        Text(
            modifier = Modifier
                .noRippleClickable { onClick() }
                .padding(top = FcbTheme.padding.smallVerticalPadding01),
            fontSize = 25.sp,
            style = FcbTheme.typography.title,
            text = count.toString()
        )
    }
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
fun UserPageScreenPreview() {
    MyPageScreen(
        modifier = Modifier.background(color = FcbTheme.colors.fcbGray),
        uiState = MyPageUiState.MyPage(
            userStats = UserStats(
                nickname = "woogi",
                userId = 6586,
                subscribingDiaryCount = 7826,
                subscribingUserCount = 5941,
                diaryCount = 5388,
                followingStatus = FollowingStatus.SUBSCRIBED
            ),
            _diaries = DiaryFixture.get()
        )
    )
}
