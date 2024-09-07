package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendRequestNotificationsResponse(
    @SerialName("data")
    val notifications: List<FriendRequestNotificationResponse>
)
