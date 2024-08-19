package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.domain.FriendRequestNotification

interface NotificationRepository {

    suspend fun fetchFriendRequestNotification(): List<FriendRequestNotification>
}
