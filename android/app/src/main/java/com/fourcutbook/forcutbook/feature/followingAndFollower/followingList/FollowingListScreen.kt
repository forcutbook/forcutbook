package com.fourcutbook.forcutbook.feature.followingAndFollower.followingList

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
import com.fourcutbook.forcutbook.data.fixture.UserProfileFixture
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.followingAndFollower.FollowerAndFollowingList

@Composable
fun FollowingListRoute(
    userId: Long?,
    followingListViewModel: FollowingListViewModel = hiltViewModel(),
    onUserProfileClick: (userId: Long) -> Unit,
    onShowSnackBar: (message: String) -> Unit,
    onBackPressed: () -> Unit
) {
    val uiState: FollowingListUiState by followingListViewModel.uiState.collectAsStateWithLifecycle()

    followingListViewModel.fetchFollowings(userId)

    FollowingListScreen(
        uiState = uiState,
        onUserProfileClick = onUserProfileClick,
        onBackClick = onBackPressed
    )
}

@Composable
fun FollowingListScreen(
    modifier: Modifier = Modifier,
    uiState: FollowingListUiState,
    onUserProfileClick: (userId: Long) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    when (uiState) {
        is FollowingListUiState.FollowingList -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = FcbTheme.padding.basicVerticalPadding)
            ) {
                FollowingListTopAppBar(
                    header = stringResource(id = FcbRoute.FollowingListRoute.headerRes),
                    onBackClick = onBackClick
                )
                FollowerAndFollowingList(
                    userProfiles = uiState.value,
                    onUserProfileClick = onUserProfileClick
                )
            }
        }

        else -> {
        }
    }
}

@Composable
fun FollowingListTopAppBar(
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
fun FollowingListScreenPreview() {
    FollowingListScreen(
        modifier = Modifier.background(FcbTheme.colors.fcbGray),
        uiState = FollowingListUiState.FollowingList(
            value = UserProfileFixture.get()
        )
    )
}
