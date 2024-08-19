package com.fourcutbook.forcutbook.data.fixture

import com.fourcutbook.forcutbook.domain.FriendRequestNotification

object NotificationFixture {

    fun get(): List<FriendRequestNotification> = listOf(
        FriendRequestNotification(
            userId = 3250,
            userNickname = "woogi",
            profileImgUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg"
        ),
        FriendRequestNotification(
            userId = 3250,
            userNickname = "woogi",
            profileImgUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg"
        ),
        FriendRequestNotification(
            userId = 3250,
            userNickname = "woogi",
            profileImgUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg"
        )
    )
}
