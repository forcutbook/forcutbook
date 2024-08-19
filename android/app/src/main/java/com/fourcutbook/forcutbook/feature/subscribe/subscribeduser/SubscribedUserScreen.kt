package com.fourcutbook.forcutbook.feature.subscribe.subscribeduser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.subscribe.SubscribeList
import com.fourcutbook.forcutbook.feature.subscribe.subscribingdiary.SubscribingDiaryTopAppBar

@Composable
fun SubscribedUserRoute(
    viewModel: SubscribedUserViewModel = hiltViewModel(),
    navigateToUserPage: () -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SubscribedUserScreen(
        uiState = uiState,
        navigateToUserPage = navigateToUserPage,
        onBackClick = onBackPressed
    )
}

@Composable
fun SubscribedUserScreen(
    modifier: Modifier = Modifier,
    uiState: SubscribedUserUiState,
    navigateToUserPage: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    when (uiState) {
        is SubscribedUserUiState.SubscribedUsers -> {
            Column(modifier = modifier.fillMaxSize()) {
                SubscribingDiaryTopAppBar(
                    header = stringResource(id = FcbRoute.SubscribingDiaryRoute.headerRes),
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
