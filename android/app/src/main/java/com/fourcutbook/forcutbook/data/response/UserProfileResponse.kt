package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    @SerialName("imageUrl")
    val profileImageUrl: String,
    @SerialName("userName")
    val nickname: String,
    @SerialName("status")
    val isSubscribing: String,
    @SerialName("userId")
    val userId: Long
)
