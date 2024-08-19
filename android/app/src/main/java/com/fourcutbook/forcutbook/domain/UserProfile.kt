package com.fourcutbook.forcutbook.domain

data class UserProfile(
    // todo: userId 받아오기
    val userId: Long = 1,
    val profileImageUrl: String,
    val nickname: String,
    val isSubscribing: Boolean = false
)
