package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendRequestNotificationResponse(
    @SerialName("userId")
    val userId: Long,
    @SerialName("userName")
    val userNickname: String
//    @SerialName("profileImgUrl")
//    val profileImgUrl: String
)
