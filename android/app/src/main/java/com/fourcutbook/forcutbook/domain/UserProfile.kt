package com.fourcutbook.forcutbook.domain

data class UserProfile(
    val profileImageUrl: String,
    val nickname: String,
    val isSubscribing: Boolean = false
)
