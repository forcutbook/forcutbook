package com.fourcutbook.forcutbook.domain

data class UserProfile(
    val userId: Long,
    val profileImageUrl: String,
    val nickname: String,
    val isSubscribing: SubscribingStatus
)
