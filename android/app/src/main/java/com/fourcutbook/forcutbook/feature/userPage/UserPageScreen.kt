package com.fourcutbook.forcutbook.feature.userPage

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.domain.SubscribingCount
import com.fourcutbook.forcutbook.domain.UserInfo
import com.fourcutbook.forcutbook.feature.FcbTopAppBarWithOnlyTitle
import com.fourcutbook.forcutbook.feature.diaryfeed.DiariesColumn
import com.fourcutbook.forcutbook.util.DiaryFixture

@Composable
fun UserPageRoute(
    modifier: Modifier = Modifier,
    userPageViewModel: UserPageViewModel = hiltViewModel(),
    userId: Long?,
    onSubscribingUserClick: (userId: Long?) -> Unit = {},
    onSubscribedUserClick: (userId: Long?) -> Unit = {},
    onDiaryClick: (diaryId: Long) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    val uiState: UserPageUiState by userPageViewModel.uiState.collectAsStateWithLifecycle()

    userId?.let { id ->
        userPageViewModel.fetchUserInfo(userId = id)
    }

    MyPageScreen(
        modifier = Modifier,
        uiState = uiState,
        onSubscribingUserClick = { onSubscribingUserClick(userId) },
        onSubscribedUserClick = { onSubscribedUserClick(userId) },
        onDiaryClick = onDiaryClick
    )
}

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    uiState: UserPageUiState,
    onSubscribingUserClick: () -> Unit = {},
    onSubscribedUserClick: () -> Unit = {},
    onDiaryClick: (diaryId: Long) -> Unit = {}
) {
    when (uiState) {
        is UserPageUiState.UserPage -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = FcbTheme.padding.basicVerticalPadding)
            ) {
                FcbTopAppBarWithOnlyTitle(title = "%s의 일기".format(uiState.value.nickname))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    UserPageSubscribingCount(
                        typeOfCount = R.string.subscribing_diary_description,
                        count = uiState.value
                            .subscribingCount
                            .subscribingDiaryCount,
                        onClick = onSubscribingUserClick
                    )
                    UserPageSubscribingCount(
                        modifier = Modifier.padding(start = 25.dp),
                        typeOfCount = R.string.subscribing_user_description,
                        count = uiState.value
                            .subscribingCount
                            .subscribingUserCount,
                        onClick = onSubscribedUserClick
                    )
                    UserPageSubscribingCount(
                        modifier = Modifier.padding(start = 25.dp),
                        typeOfCount = R.string.count_of_diaries,
                        count = uiState.value.diaryCount
                    )
                }
                DiariesColumn(
                    diaries = uiState.value.diaries,
                    onDiaryClick = onDiaryClick
                )
            }
        }

        else -> {
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
        // todo: style 정의
        Text(
            modifier = Modifier.clickable { onClick() },
            fontSize = 14.sp,
            style = FcbTheme.typography.heading,
            text = stringResource(typeOfCount)
        )
        Text(
            modifier = Modifier
                .clickable { onClick() }
                .padding(top = FcbTheme.padding.smallVerticalPadding),
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
        uiState = UserPageUiState.UserPage(
            value = UserInfo(
                diaries = DiaryFixture.get(),
                userId = 1,
                subscribingCount = SubscribingCount(
                    subscribingDiaryCount = 4442,
                    subscribingUserCount = 4953
                )
            )
        )
    )
}
