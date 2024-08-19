package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.Serializable

@Serializable
data class FriendRequestNotificationsResponse(
    val notifications: List<FriendRequestNotificationResponse>
)
