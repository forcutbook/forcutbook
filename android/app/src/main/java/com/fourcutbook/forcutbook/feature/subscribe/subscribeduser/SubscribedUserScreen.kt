package com.fourcutbook.forcutbook.feature.subscribe.subscribeduser

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
import com.fourcutbook.forcutbook.feature.subscribe.SubscribeList
import com.fourcutbook.forcutbook.feature.subscribe.subscribingdiary.SubscribingDiaryTopAppBar

@Composable
fun SubscribedUserRoute(
    subscribedUserViewModel: SubscribedUserViewModel = hiltViewModel(),
    userId: Long?,
    onProfileClick: (userId: Long) -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    val uiState by subscribedUserViewModel.uiState.collectAsStateWithLifecycle()

    subscribedUserViewModel.fetchSubscribingDiaries(userId)

    SubscribedUserScreen(
        uiState = uiState,
        onProfileClick = onProfileClick,
        onBackClick = onBackPressed
    )
}

@Composable
fun SubscribedUserScreen(
    modifier: Modifier = Modifier,
    uiState: SubscribedUserUiState,
    onProfileClick: (userId: Long) -> Unit,
    onBackClick: () -> Unit = {}
) {
    when (uiState) {
        is SubscribedUserUiState.SubscribedUsers -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = FcbTheme.padding.basicVerticalPadding)
            ) {
                SubscribingDiaryTopAppBar(
                    header = stringResource(R.string.header_of_subscribed_user),
                    onBackClick = onBackClick
                )
                SubscribeList(
                    userProfiles = uiState.value,
                    onUserProfileClick = onProfileClick
                )
            }
        }

        else -> {
        }
    }
}
