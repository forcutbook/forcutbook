package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendRequestNotificationListResponse(
    @SerialName("data")
    val value: List<FriendRequestNotificationResponse>
)
