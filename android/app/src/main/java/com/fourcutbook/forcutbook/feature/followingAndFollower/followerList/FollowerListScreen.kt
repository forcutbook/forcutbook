package com.fourcutbook.forcutbook.feature.followingAndFollower.followerList

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.feature.followingAndFollower.FollowerAndFollowingList
import com.fourcutbook.forcutbook.feature.followingAndFollower.followingList.FollowingListTopAppBar

@Composable
fun FollowerListRoute(
    viewModel: FollowerListViewModel = hiltViewModel(),
    userId: Long?,
    onProfileClick: (userId: Long) -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (uiState is FollowerListUiState.Empty) {
        viewModel.fetchFollowers(userId)
    }
    LaunchedEffect(key1 = null) {
        viewModel.event.collect { event ->
            val message = when (event) {
                is FollowerListEvent.Canceled -> context.getString(R.string.follower_list_follower_cancel)

                is FollowerListEvent.Error -> context.getString(R.string.common_error_description)

                else -> ""
            }
            if (message.isNotEmpty()) onShowSnackBar(message)
        }
    }
    FollowerListScreen(
        uiState = uiState,
        onProfileClick = onProfileClick,
        onRequestFollowingButtonClick = { followingId ->
            viewModel.postFollowingRequest(
                userIdOfFollowing = followingId,
                userIdOfPageOwner = userId
            )
        },
        onCancelRequestFollowingButtonClick = { followingId ->
            viewModel.postFollowingRequestCancel(
                userIdOfFollowing = followingId,
                userIdOfPageOwner = userId
            )
        },
        onCancelFollowingButtonClick = { followingId ->
            viewModel.deleteFollowing(
                userIdOfFollowing = followingId,
                userIdOfPageOwner = userId
            )
        },
        onCancelFollowerButtonClick = { followerId ->
            viewModel.deleteFollower(followerId)
        },
        onBackClick = onBackPressed
    )
}

@Composable
fun FollowerListScreen(
    modifier: Modifier = Modifier,
    uiState: FollowerListUiState,
    onProfileClick: (userId: Long) -> Unit,
    onRequestFollowingButtonClick: (userId: Long) -> Unit,
    onCancelRequestFollowingButtonClick: (userId: Long) -> Unit,
    onCancelFollowingButtonClick: (userId: Long) -> Unit,
    onCancelFollowerButtonClick: (userId: Long) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    when (uiState) {
        is FollowerListUiState.FollowerList -> {
            Log.d("woogi", "FollowerListScreen: ${uiState is FollowerListUiState.FollowerList.MyFollower}")
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
                    onUserProfileClick = onProfileClick,
                    isForFollowerCancel = uiState is FollowerListUiState.FollowerList.MyFollower,
                    onRequestFollowingButtonClick = onRequestFollowingButtonClick,
                    onCancelRequestFollowingButtonClick = onCancelRequestFollowingButtonClick,
                    onCancelFollowingButtonClick = onCancelFollowingButtonClick,
                    onCancelFollowerButtonClick = onCancelFollowerButtonClick
                )
            }
        }

        else -> {
        }
    }
}
