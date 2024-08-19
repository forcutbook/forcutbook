package com.fourcutbook.forcutbook.data.fixture

import com.fourcutbook.forcutbook.domain.FriendRequestNotification

object NotificationFixture {

    fun get(): List<FriendRequestNotification> = listOf(
        FriendRequestNotification(
            userId = 3250,
            userNickname = "woogi",
            profileImgUrl = "https://duckduckgo.com/?q=expetenda"
        ),
        FriendRequestNotification(
            userId = 3250,
            userNickname = "woogi",
            profileImgUrl = "https://duckduckgo.com/?q=expetenda"
        ),
        FriendRequestNotification(
            userId = 3250,
            userNickname = "woogi",
            profileImgUrl = "https://duckduckgo.com/?q=expetenda"
        )
    )
}
