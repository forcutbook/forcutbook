package com.fourcutbook.forcutbook.domain

data class FriendRequestNotification(
    val userId: Long,
    val profileImgUrl: String,
    val userNickname: String
)
