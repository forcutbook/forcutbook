package com.fourcutbook.forcutbook.feature.followingAndFollower.followerList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.feature.followingAndFollower.FollowerAndFollowingList
import com.fourcutbook.forcutbook.feature.followingAndFollower.followingList.FollowingListTopAppBar

@Composable
fun FollowerListRoute(
    followerListViewModel: FollowerListViewModel = hiltViewModel(),
    userId: Long?,
    onProfileClick: (userId: Long) -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    val uiState by followerListViewModel.uiState.collectAsStateWithLifecycle()

    followerListViewModel.fetchSubscribingDiaries(userId)

    FollowerListScreen(
        uiState = uiState,
        onProfileClick = onProfileClick,
        onBackClick = onBackPressed
    )
}

@Composable
fun FollowerListScreen(
    modifier: Modifier = Modifier,
    uiState: FollowerListUiState,
    onProfileClick: (userId: Long) -> Unit,
    onBackClick: () -> Unit = {}
) {
    when (uiState) {
        is FollowerListUiState.SubscribedUsers -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = FcbTheme.padding.basicVerticalPadding)
            ) {
                FollowingListTopAppBar(
                    header = stringResource(R.string.header_of_subscribed_user),
                    onBackClick = onBackClick
                )
                FollowerAndFollowingList(
                    userProfiles = uiState.value,
                    onUserProfileClick = onProfileClick
                )
            }
        }

        else -> {
        }
    }
}
