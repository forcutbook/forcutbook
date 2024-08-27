package com.fourcutbook.forcutbook.data.service

import com.fourcutbook.forcutbook.data.response.BaseResponse
import com.fourcutbook.forcutbook.data.response.FriendRequestNotificationsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationService {

    @GET("/users/{userId}/friends/accept")
    suspend fun fetchFriendRequestNotification(
        @Path(value = "userId") userId: Long
    ): Response<BaseResponse<FriendRequestNotificationsResponse>>
}
