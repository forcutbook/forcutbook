package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("nickname")
    val nickname: String
)
