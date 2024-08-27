package com.fourcutbook.forcutbook.feature.searching

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.fourcutbook.forcutbook.domain.SubscribingStatus
import com.fourcutbook.forcutbook.domain.UserProfile
import com.fourcutbook.forcutbook.feature.FcbTopAppBarWithBackButton
import com.fourcutbook.forcutbook.util.NicknameSearchingTextField
import com.fourcutbook.forcutbook.util.noRippleClickable

@Composable
fun UserSearchingRoute(
    viewModel: UserSearchingViewModel = hiltViewModel(),
    onUserProfileClick: (userId: Long) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchingNickname by viewModel.searchingNickname.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = null) {
        viewModel.event.collect { event ->
            when (event) {
                is UserSearchingEvent.FollowingRequest -> onShowSnackBar(context.getString(R.string.success_following_request))

                is UserSearchingEvent.FollowingRequestCancel -> onShowSnackBar(context.getString(R.string.success_cancel_following_request))

                is UserSearchingEvent.Error -> onShowSnackBar(context.getString(R.string.common_error_description))
            }
        }
    }

    UserSearchingScreen(
        modifier = Modifier,
        uiState = uiState,
        searchingNickname = searchingNickname,
        onFollowingRequestClick = viewModel::postFollowing,
        onFollowingRequestCancelClick = viewModel::postFollowingRequestCancel,
        onUserProfileClick = onUserProfileClick,
        onSearch = viewModel::enterNickname,
        onBackClick = onBackClick
    )
}

@Composable
fun UserSearchingScreen(
    modifier: Modifier = Modifier,
    uiState: UserSearchingUiState,
    searchingNickname: String,
    onUserProfileClick: (userId: Long) -> Unit = {},
    onFollowingRequestClick: (userId: Long, nickname: String) -> Unit = { _, _ -> },
    onFollowingRequestCancelClick: (userId: Long, nickname: String) -> Unit = { _, _ -> },
    onSearch: (nickname: String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = FcbTheme.padding.basicVerticalPadding)
    ) {
        FcbTopAppBarWithBackButton(
            title = stringResource(id = R.string.header_of_user_searching),
            onBackClick = onBackClick
        )
        NicknameSearchingTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = FcbTheme.padding.basicVerticalPadding)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            value = searchingNickname,
            isFocused = isFocused,
            onValueChange = onSearch
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = FcbTheme.padding.smallVerticalPadding01)
        ) {
            items(uiState.userProfiles) { userProfile ->
                UserProfileItem(
                    userProfile = userProfile,
                    onUserProfileClick = { onUserProfileClick(userProfile.userId) },
                    onFollowingRequestClick = {
                        onFollowingRequestClick(
                            userProfile.userId,
                            searchingNickname
                        )
                    },
                    onFollowingRequestCancelClick = {
                        onFollowingRequestCancelClick(
                            userProfile.userId,
                            searchingNickname
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun UserProfileItem(
    modifier: Modifier = Modifier,
    userProfile: UserProfile,
    onUserProfileClick: () -> Unit,
    onFollowingRequestClick: () -> Unit,
    onFollowingRequestCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = FcbTheme.padding.smallVerticalPadding02)
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
                .noRippleClickable { onUserProfileClick() },
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
                when (userProfile.isSubscribing) {
                    SubscribingStatus.REQUESTED, SubscribingStatus.NONE -> {
                        val text = when (userProfile.isSubscribing) {
                            SubscribingStatus.NONE -> stringResource(R.string.description_of_request_subscribing)
                            SubscribingStatus.REQUESTED -> stringResource(R.string.descripion_of_requesting_status)
                            else -> ""
                        }

                        Text(
                            modifier = Modifier
                                .clickable {
                                    when (userProfile.isSubscribing) {
                                        SubscribingStatus.NONE -> onFollowingRequestClick()

                                        SubscribingStatus.REQUESTED -> onFollowingRequestCancelClick()

                                        else -> {}
                                    }
                                }
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

                    SubscribingStatus.SUBSCRIBED -> {
                        Icon(
                            tint = FcbTheme.colors.fcbLightBeige,
                            painter = painterResource(id = R.drawable.ic_checked),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
fun UserSearchingScreenPreview() {
    UserSearchingScreen(
        modifier = Modifier,
        uiState = UserSearchingUiState(
            userProfiles = listOf(
                UserProfile(
                    userId = 8513,
                    profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
                    nickname = "woogie",
                    isSubscribing = SubscribingStatus.REQUESTED
                ),
                UserProfile(
                    userId = 8513,
                    profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
                    nickname = "woogie",
                    isSubscribing = SubscribingStatus.NONE
                ),
                UserProfile(
                    userId = 8513,
                    profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
                    nickname = "woogie",
                    isSubscribing = SubscribingStatus.SUBSCRIBED
                )
            )
        ),
        searchingNickname = "woogi"
    )
}
