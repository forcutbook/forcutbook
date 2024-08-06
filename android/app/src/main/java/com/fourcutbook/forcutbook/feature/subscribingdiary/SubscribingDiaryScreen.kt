package com.fourcutbook.forcutbook.feature.subscribingdiary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.domain.UserProfile
import com.fourcutbook.forcutbook.feature.FcbRoute

@Composable
fun SubscribingDiaryRoute(
    subscribingDiaryViewModel: SubscribingDiaryViewModel = hiltViewModel(),
    navigateToUserPage: () -> Unit,
    onShowSnackBar: (message: String) -> Unit,
    onBackPressed: () -> Unit
) {
    val uiState: SubscribingDiaryUiState by subscribingDiaryViewModel.uiState.collectAsStateWithLifecycle()

    SubscribingDiaryScreen(
        uiState = uiState,
        navigateToUserPage = navigateToUserPage,
        onBackClick = onBackPressed
    )
}

@Composable
fun SubscribingDiaryScreen(
    modifier: Modifier = Modifier,
    uiState: SubscribingDiaryUiState,
    navigateToUserPage: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    when (uiState) {
        is SubscribingDiaryUiState.SubscribingDiaries -> {
            Column(modifier = modifier.fillMaxSize()) {
                SubscribingDiaryTopAppBar(
                    header = stringResource(id = FcbRoute.SubscribingDiaryRoute.header),
                    onBackClick = onBackClick
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = FcbTheme.padding.basicVerticalPadding)
                ) {
                    items(uiState.subscribingDiaries) { userProfile ->
                        SubscribingDiaryItem(
                            userProfile = userProfile,
                            navigateToUserPage = navigateToUserPage
                        )
                    }
                }
            }
        }

        else -> {
        }
    }
}

@Composable
fun SubscribingDiaryItem(
    userProfile: UserProfile,
    navigateToUserPage: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(vertical = FcbTheme.padding.smallVerticalPadding)
            .background(shape = RoundedCornerShape(5.dp), color = FcbTheme.colors.fcbWhite)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(start = FcbTheme.padding.basicHorizontalPadding)
                .size(FcbTheme.shame.iconSize)
                .clip(RoundedCornerShape(5.dp))
                .clickable { navigateToUserPage() },
            model = userProfile.profileImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Row(
            modifier = Modifier
                .padding(
                    horizontal = FcbTheme.padding.basicHorizontalPadding,
                    vertical = 8.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = userProfile.nickname,
                style = FcbTheme.typography.body,
                fontSize = 14.sp
            )
            Surface {
                val text = if (userProfile.isSubscribing) {
                    stringResource(R.string.subscribing_cancel)
                } else {
                    stringResource(R.string.subscribing_request)
                }

                Text(
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(5.dp),
                            color = FcbTheme.colors.fcbDarkBeige
                        )
                        .padding(vertical = 6.dp, horizontal = 4.dp),
                    text = text,
                    style = FcbTheme.typography.body,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun SubscribingDiaryTopAppBar(
    header: String,
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
            text = header
        )
    }
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
fun SubscribingScreenPreview() {
    SubscribingDiaryScreen(
        modifier = Modifier.background(FcbTheme.colors.fcbGray),
        uiState = SubscribingDiaryUiState.SubscribingDiaries(
            subscribingDiaries = listOf(
                UserProfile(
                    profileImageUrl = "https://duckduckgo.com/?q=consectetuer",
                    nickname = "woogi"
                ),
                UserProfile(
                    profileImageUrl = "https://duckduckgo.com/?q=consectetuer",
                    nickname = "woogi"
                ),
                UserProfile(
                    profileImageUrl = "https://duckduckgo.com/?q=consectetuer",
                    nickname = "woogi"
                ),
                UserProfile(
                    profileImageUrl = "https://duckduckgo.com/?q=consectetuer",
                    nickname = "woogi"
                )
            )
        )
    )
}
