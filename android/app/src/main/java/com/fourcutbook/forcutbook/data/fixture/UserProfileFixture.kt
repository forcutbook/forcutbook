package com.fourcutbook.forcutbook.data.fixture

import com.fourcutbook.forcutbook.domain.FollowingStatus
import com.fourcutbook.forcutbook.domain.UserProfile

object UserProfileFixture {

    fun get(): List<UserProfile> = listOf(
        UserProfile(
            profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
            nickname = "woogi",
            userId = 2936,
            followingStatus = FollowingStatus.NONE
        ),
        UserProfile(
            profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
            nickname = "woogi",
            userId = 4925,
            followingStatus = FollowingStatus.SUBSCRIBED
        ),
        UserProfile(
            profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
            nickname = "woogi",
            userId = 1731,
            followingStatus = FollowingStatus.SUBSCRIBED
        ),
        UserProfile(
            profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
            nickname = "woogi",
            userId = 7479,
            followingStatus = FollowingStatus.NONE
        )
    )
}
