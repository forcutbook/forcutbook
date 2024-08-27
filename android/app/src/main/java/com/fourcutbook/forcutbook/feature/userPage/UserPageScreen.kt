package com.fourcutbook.forcutbook.feature.userPage

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.domain.SubscribingStatus
import com.fourcutbook.forcutbook.domain.UserStats
import com.fourcutbook.forcutbook.feature.FcbTopAppBarWithBackButton
import com.fourcutbook.forcutbook.feature.diaryfeed.DiariesColumn
import com.fourcutbook.forcutbook.util.DiaryFixture
import com.fourcutbook.forcutbook.util.noRippleClickable

@Composable
fun UserPageRoute(
    modifier: Modifier = Modifier,
    userPageViewModel: UserPageViewModel = hiltViewModel(),
    userId: Long?,
    onFollowingCountClick: (userId: Long?) -> Unit = {},
    onFollowerCountClick: (userId: Long?) -> Unit = {},
    onDiaryClick: (diaryId: Long) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState: UserPageUiState by userPageViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState is UserPageUiState.UnLoaded) {
        userId?.let { id ->
            userPageViewModel.fetchUserStatsAndDiaries(id)
        }
    }
    UserPageScreen(
        modifier = Modifier,
        uiState = uiState,
        onFollowingCountClick = { onFollowingCountClick(userId) },
        onFollowerCountClick = { onFollowerCountClick(userId) },
        onFollowingRequestButtonClick = {
            userId?.let {
                userPageViewModel.postFollowingRequest(it)
            }
        },
        onFollowingRequestCancelButtonClick = {
            userId?.let {
                userPageViewModel.deleteFollowingRequest(it)
            }
        },
        onDiaryClick = onDiaryClick,
        onBackClick = onBackClick
    )
}

@Composable
fun UserPageScreen(
    modifier: Modifier = Modifier,
    uiState: UserPageUiState,
    onFollowingCountClick: () -> Unit = {},
    onFollowerCountClick: () -> Unit = {},
    onDiaryClick: (diaryId: Long) -> Unit = {},
    onFollowingRequestButtonClick: () -> Unit = {},
    onFollowingRequestCancelButtonClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    if (uiState is UserPageUiState.UserStatsLoaded) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = FcbTheme.padding.basicVerticalPadding)
        ) {
            uiState.userStats.let { userStats ->
                FcbTopAppBarWithBackButton(
                    title = "%s의 일기".format(userStats.nickname),
                    onBackClick = onBackClick
                )
                UserStatsRow(
                    userStats = userStats,
                    onFollowerCountClick = onFollowerCountClick,
                    onFollowingCountClick = onFollowingCountClick
                )
            }
            when (uiState) {
                is UserPageUiState.UserStatsLoaded.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.CenterHorizontally),
                            color = FcbTheme.colors.fcbDarkBeige
                        )
                    }
                }

                is UserPageUiState.UserStatsLoaded.NotSubscribed -> {
                    Icon(
                        modifier = Modifier
                            .padding(top = FcbTheme.padding.basicVerticalPadding)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.ic_locked),
                        tint = FcbTheme.colors.fcbDarkBeige02,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = FcbTheme.padding.smallVerticalPadding01)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.request_subscribing_wanna_read_diary),
                        style = FcbTheme.typography.body02,
                        fontWeight = SemiBold
                    )
                    val buttonDescription = when (uiState.userStats.subscribingStatus) {
                        SubscribingStatus.NONE -> "구독 신청"
                        SubscribingStatus.REQUESTED -> "구독 신청 취소"
                        else -> "구독 신청"
                    }
                    Text(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .background(
                                color = FcbTheme.colors.fcbLightBeige02,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(vertical = FcbTheme.padding.smallVerticalPadding01)
                            .noRippleClickable {
                                when (uiState.userStats.subscribingStatus) {
                                    SubscribingStatus.NONE -> onFollowingRequestButtonClick()
                                    SubscribingStatus.REQUESTED -> onFollowingRequestCancelButtonClick()
                                    else -> {}
                                }
                            },
                        textAlign = TextAlign.Center,
                        text = buttonDescription,
                        style = FcbTheme.typography.body02
                    )
                }

                is UserPageUiState.UserStatsLoaded.Subscribed -> {
                    DiariesColumn(
                        diaries = uiState.diaries,
                        onDiaryClick = onDiaryClick
                    )
                }

                else -> {
                }
            }
        }
    }
}

@Composable
fun UserStatsRow(
    modifier: Modifier = Modifier,
    userStats: UserStats,
    onFollowingCountClick: () -> Unit = {},
    onFollowerCountClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = FcbTheme.padding.basicVerticalPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FollowingFollowerCount(
            modifier = Modifier.padding(start = FcbTheme.padding.basicHorizontalPadding),
            typeOfCount = R.string.subscribing_diary_description,
            count = userStats.subscribingDiaryCount,
            onClick = onFollowingCountClick
        )
        FollowingFollowerCount(
            typeOfCount = R.string.subscribing_user_description,
            count = userStats.subscribingUserCount,
            onClick = onFollowerCountClick
        )
        FollowingFollowerCount(
            modifier = Modifier.padding(end = FcbTheme.padding.basicHorizontalPadding),
            typeOfCount = R.string.count_of_diaries,
            count = userStats.diaryCount
        )
    }
}

@Composable
fun FollowingFollowerCount(
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
fun UserPageNotSubscribedScreenPreview() {
    UserPageScreen(
        modifier = Modifier.background(color = FcbTheme.colors.fcbGray),
        uiState = UserPageUiState.UserStatsLoaded.NotSubscribed(
            userStats = UserStats(
                nickname = "woogie",
                userId = 0,
                subscribingDiaryCount = 10,
                subscribingUserCount = 10,
                diaryCount = 15,
                subscribingStatus = SubscribingStatus.NONE
            )
        )
    )
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
fun UserPageSubscribeScreenPreview() {
    UserPageScreen(
        modifier = Modifier.background(color = FcbTheme.colors.fcbGray),
        uiState = UserPageUiState.UserStatsLoaded.Subscribed(
            userStats = UserStats(
                nickname = "woogie",
                subscribingDiaryCount = 10,
                subscribingUserCount = 10,
                diaryCount = 15,
                userId = 0,
                subscribingStatus = SubscribingStatus.SUBSCRIBED
            ),
            _diaries = DiaryFixture.get()
        )
    )
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
fun UserPageLoadingScreenPreview() {
    UserPageScreen(
        modifier = Modifier.background(color = FcbTheme.colors.fcbGray),
        uiState = UserPageUiState.UserStatsLoaded.Loading(
            userStats = UserStats(
                nickname = "woogie",
                subscribingDiaryCount = 10,
                subscribingUserCount = 10,
                diaryCount = 15,
                userId = 0,
                subscribingStatus = SubscribingStatus.SUBSCRIBED
            )
        )
    )
}
