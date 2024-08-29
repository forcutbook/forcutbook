package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FollowingListResponse(
    @SerialName("userId")
    val userId: Long,
    @SerialName("userName")
    val nickname: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("data")
    val followings: List<UserProfileResponse>
)
