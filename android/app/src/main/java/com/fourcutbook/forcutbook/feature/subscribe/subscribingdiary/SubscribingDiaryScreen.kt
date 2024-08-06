package com.fourcutbook.forcutbook.feature.subscribe.subscribingdiary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.domain.UserProfile
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.subscribe.SubscribeList

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
                SubscribeList(
                    userProfiles = uiState.value,
                    navigateToUserPage = navigateToUserPage
                )
            }
        }

        else -> {
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
            value = listOf(
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