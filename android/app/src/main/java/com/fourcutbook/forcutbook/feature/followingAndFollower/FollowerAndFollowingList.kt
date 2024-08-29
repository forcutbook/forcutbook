package com.fourcutbook.forcutbook.feature.followingAndFollower

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.forcutbook.forcutbook.R
import com.fourcutbook.forcutbook.design.FcbTheme
import com.fourcutbook.forcutbook.domain.FollowingStatus
import com.fourcutbook.forcutbook.domain.UserProfile
import com.fourcutbook.forcutbook.util.noRippleClickable

@Composable
fun FollowerAndFollowingList(
    userProfiles: List<UserProfile>,
    isForFollowerCancel: Boolean,
    onUserProfileClick: (userId: Long) -> Unit,
    onRequestFollowingButtonClick: (userId: Long) -> Unit,
    onCancelRequestFollowingButtonClick: (userId: Long) -> Unit,
    onCancelFollowingButtonClick: (userId: Long) -> Unit,
    onCancelFollowerButtonClick: (userId: Long) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = FcbTheme.padding.basicVerticalPadding)
    ) {
        items(userProfiles) { userProfile ->
            FollowerAndFollowingItem(
                userProfile = userProfile,
                isForFollowerCancel = isForFollowerCancel,
                onUserProfileClick = {
                    onUserProfileClick(userProfile.userId)
                },
                onRequestFollowingButtonClick = {
                    onRequestFollowingButtonClick(userProfile.userId)
                },
                onCancelRequestFollowingButtonClick = {
                    onCancelRequestFollowingButtonClick(userProfile.userId)
                },
                onCancelFollowingButtonClick = {
                    onCancelFollowingButtonClick(userProfile.userId)
                },
                onCancelFollowerButtonClick = {
                    onCancelFollowerButtonClick(userProfile.userId)
                }
            )
        }
    }
}

@Composable
fun FollowerAndFollowingItem(
    userProfile: UserProfile,
    isForFollowerCancel: Boolean,
    onUserProfileClick: () -> Unit,
    onRequestFollowingButtonClick: () -> Unit,
    onCancelRequestFollowingButtonClick: () -> Unit,
    onCancelFollowingButtonClick: () -> Unit,
    onCancelFollowerButtonClick: () -> Unit
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
            if (isForFollowerCancel) {
                FollowerCancelButton(onCancelButtonClick = onCancelFollowerButtonClick)
            } else {
                FollowingRequestButton(
                    status = userProfile.followingStatus,
                    onRequestFollowingButtonClick = onRequestFollowingButtonClick,
                    onCancelRequestFollowingButtonClick = onCancelRequestFollowingButtonClick,
                    onCancelFollowingButtonClick = onCancelFollowingButtonClick
                )
            }
        }
    }
}

@Composable
fun FollowingRequestButton(
    status: FollowingStatus,
    onRequestFollowingButtonClick: () -> Unit,
    onCancelRequestFollowingButtonClick: () -> Unit,
    onCancelFollowingButtonClick: () -> Unit
) {
    val text = when (status) {
        FollowingStatus.NONE -> stringResource(id = R.string.subscribing_request)
        FollowingStatus.SUBSCRIBED -> stringResource(id = R.string.following_cancel)
        FollowingStatus.REQUESTED -> stringResource(id = R.string.descripion_of_requesting_status)
    }

    Surface {
        Text(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(5.dp),
                    color = FcbTheme.colors.fcbDarkBeige
                )
                .padding(vertical = 6.dp, horizontal = 4.dp)
                .noRippleClickable {
                    when (status) {
                        FollowingStatus.NONE -> onRequestFollowingButtonClick()
                        FollowingStatus.SUBSCRIBED -> onCancelFollowingButtonClick()
                        FollowingStatus.REQUESTED -> onCancelRequestFollowingButtonClick()
                    }
                },
            text = text,
            style = FcbTheme.typography.body,
            fontSize = 12.sp
        )
    }
}

@Composable
fun FollowerCancelButton(onCancelButtonClick: () -> Unit) {
    Surface {
        Text(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(5.dp),
                    color = FcbTheme.colors.fcbDarkBeige
                )
                .padding(vertical = 6.dp, horizontal = 4.dp)
                .noRippleClickable { onCancelButtonClick() },
            text = stringResource(id = R.string.follower_list_delete_follower),
            style = FcbTheme.typography.body,
            fontSize = 12.sp
        )
    }
}
