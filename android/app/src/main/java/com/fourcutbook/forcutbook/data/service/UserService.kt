package com.fourcutbook.forcutbook.data.service

import com.fourcutbook.forcutbook.data.response.BaseResponse
import com.fourcutbook.forcutbook.data.response.SubscribingDiaryListResponse
import com.fourcutbook.forcutbook.data.response.SubscribingResponse
import com.fourcutbook.forcutbook.data.response.UserProfileResponse
import com.fourcutbook.forcutbook.data.response.UserStatsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("/users/{userId}")
    suspend fun fetchUsers(
        @Path(value = "userId") userId: Long,
        @Query("search") userName: String
    ): Response<BaseResponse<List<UserProfileResponse>>>

    @GET("/{userId}/{friendId}")
    suspend fun fetchUserStats(
        @Path(value = "userId") userId: Long,
        @Path(value = "friendId") friendId: Long
    ): Response<BaseResponse<UserStatsResponse>>

    @POST("/users/{userId}/friends/{friendId}/request")
    suspend fun postSubscribingRequest(
        @Path(value = "userId") userId: Long,
        @Path(value = "friendId") friendId: Long
    ): Response<BaseResponse<SubscribingResponse>>

    @POST("/users/{userId}/friends/{friendId}/request/cancel")
    suspend fun deleteSubscribingRequest(
        @Path(value = "userId") userId: Long,
        @Path(value = "friendId") friendId: Long
    ): Response<BaseResponse<SubscribingResponse>>

    @GET("/users/{userId}/friends/follower")
    suspend fun fetchSubscribingDiaries(
        @Path(value = "userId") userId: Long
    ): Response<BaseResponse<SubscribingDiaryListResponse>>

    @POST("/users/{userId}/friends/{friendId}/deleted")
    suspend fun deleteSubscribingDiary(
        @Path(value = "userId") userId: Long,
        @Path(value = "friendId") friendId: Long
    ): Response<BaseResponse<DeleteSubscribingResponse>>
}
