package com.fourcutbook.forcutbook.feature.notification

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.data.fixture.NotificationFixture
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.domain.FriendRequestNotification
import com.fourcutbook.forcutbook.feature.FcbRoute
import com.fourcutbook.forcutbook.feature.FcbTopAppBarWithBackButton
import com.fourcutbook.forcutbook.util.noRippleClickable

@Composable
fun NotificationRoute(
    viewModel: NotificationViewModel = hiltViewModel(),
    onProfileImgClick: (userId: Long) -> Unit = {},
    onBackClick: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    val uiState: NotificationUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = null) {
        viewModel.event.collect { event ->
            when (event) {
                is NotificationEvent.Accepted -> onShowSnackBar(context.getString(R.string.accept_following_request))

                is NotificationEvent.Denied -> onShowSnackBar(context.getString(R.string.deny_following_request))

                is NotificationEvent.Error -> onShowSnackBar(context.getString(R.string.common_error_description))
            }
        }
    }

    NotificationScreen(
        notificationUiState = uiState,
        onUserProfileClick = onProfileImgClick,
        onAcceptButtonClick = viewModel::postAcceptFollowingRequest,
        onDenyButtonClick = viewModel::postDenyFollowingRequest,
        onBackClick = onBackClick
    )
}

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    notificationUiState: NotificationUiState,
    onUserProfileClick: (userId: Long) -> Unit = {},
    onAcceptButtonClick: (userId: Long) -> Unit = {},
    onDenyButtonClick: (userId: Long) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = FcbTheme.padding.basicVerticalPadding)
    ) {
        FcbTopAppBarWithBackButton(
            title = stringResource(id = FcbRoute.NotificationRoute.headerRes),
            onBackClick = onBackClick
        )
        NotificationList(
            notifications = notificationUiState.friendRequestNotifications,
            onUserProfileClick = onUserProfileClick,
            onAcceptButtonClick = onAcceptButtonClick,
            onDenyButtonClick = onDenyButtonClick
        )
    }
}

@Composable
fun NotificationList(
    notifications: List<FriendRequestNotification>,
    onUserProfileClick: (userId: Long) -> Unit,
    onAcceptButtonClick: (userId: Long) -> Unit,
    onDenyButtonClick: (userId: Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = FcbTheme.padding.basicVerticalPadding)
    ) {
        items(notifications) { notification ->
            NotificationItem(
                notification = notification,
                onUserProfileClick = onUserProfileClick,
                onAcceptButtonClick = onAcceptButtonClick,
                onDenyButtonClick = onDenyButtonClick
            )
        }
    }
}

@Composable
fun NotificationItem(
    notification: FriendRequestNotification,
    onUserProfileClick: (userId: Long) -> Unit = {},
    onAcceptButtonClick: (userId: Long) -> Unit = {},
    onDenyButtonClick: (userId: Long) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(vertical = FcbTheme.padding.smallVerticalPadding01)
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
                .clip(shape = CircleShape)
                .clickable { onUserProfileClick(notification.userId) },
            model = notification,
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
                text = stringResource(R.string.friend_request_notification).format(notification.userNickname),
                style = FcbTheme.typography.body,
                fontSize = 14.sp
            )
            Row {
                FriendRequestAcceptOrDeclineButton(
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(5.dp),
                            color = FcbTheme.colors.fcbLightBeige
                        )
                        .noRippleClickable { onAcceptButtonClick(notification.userId) },
                    description = stringResource(R.string.friend_request_accept)
                )
                FriendRequestAcceptOrDeclineButton(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .background(
                            shape = RoundedCornerShape(5.dp),
                            color = FcbTheme.colors.fcbDarkBeige
                        )
                        .noRippleClickable { onDenyButtonClick(notification.userId) },
                    description = stringResource(R.string.friend_request_decline)
                )
            }
        }
    }
}

@Composable
fun FriendRequestAcceptOrDeclineButton(
    modifier: Modifier = Modifier,
    description: String,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.noRippleClickable { onClick() }
    ) {
        Text(
            modifier = modifier
                .padding(vertical = 6.dp, horizontal = 8.dp),
            text = description,
            style = FcbTheme.typography.body,
            fontSize = 12.sp
        )
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(
        modifier = Modifier.background(FcbTheme.colors.fcbGray),
        notificationUiState = NotificationUiState(
            friendRequestNotifications = NotificationFixture.get()
        )
    )
}

@Preview
@Composable
fun NotificationItemPreview() {
    NotificationItem(
        notification = FriendRequestNotification(
            userId = 100L,
            profileImgUrl = "https://duckduckgo.com/?q=salutatus",
            userNickname = "woogi"
        )
    )
}
