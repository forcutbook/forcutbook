package com.fourcutbook.forcutbook.feature.subscribe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.fourcutbook.forcutbook.domain.UserProfile

@Composable
fun SubscribeList(
    userProfiles: List<UserProfile>,
    navigateToUserPage: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = FcbTheme.padding.basicVerticalPadding)
    ) {
        items(userProfiles) { userProfile ->
            SubscribeItem(
                userProfile = userProfile,
                navigateToUserPage = navigateToUserPage
            )
        }
    }
}

@Composable
fun SubscribeItem(
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
                .clip(shape = CircleShape)
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
