package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    @SerialName("userId")
    val userId: Long,
    @SerialName("userName")
    val nickname: String,
    @SerialName("imageUrl")
    val profileImageUrl: String = "abcd",
    @SerialName("status")
    val isSubscribing: String
)
