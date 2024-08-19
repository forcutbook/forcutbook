package com.fourcutbook.forcutbook.data.service

import com.fourcutbook.forcutbook.data.response.FriendRequestNotificationResponse
import retrofit2.Response
import retrofit2.http.GET

interface NotificationService {

    @GET("/users/friends/request")
    suspend fun fetchFriendRequestNotification(userId: Long): Response<FriendRequestNotificationResponse>
}
