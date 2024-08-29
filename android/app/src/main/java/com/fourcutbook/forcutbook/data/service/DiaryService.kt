package com.fourcutbook.forcutbook.data.service

import com.fourcutbook.forcutbook.data.response.BaseResponse
import com.fourcutbook.forcutbook.data.response.DiariesResponse
import com.fourcutbook.forcutbook.data.response.DiaryDetailResponse
import com.fourcutbook.forcutbook.data.response.DiaryRegistrationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface DiaryService {

    @GET("/users/{userId}/diaries/feed")
    suspend fun fetchAllDiaries(
        @Path(value = "userId") userId: Long
    ): Response<BaseResponse<DiariesResponse>>

    @GET("/users/{userId}/diaries")
    suspend fun fetchMyDiaries(
        @Path(value = "userId") userId: Long
    ): Response<BaseResponse<DiariesResponse>>

    @GET("/users/{userId}/diaries/friends/{friendId}")
    suspend fun fetchUserDiaries(
        @Path(value = "userId") userId: Long,
        @Path(value = "friendId") friendId: Long
    ): Response<BaseResponse<DiariesResponse>>

    @GET("/users/{userId}/diaries/{diaryId}")
    suspend fun fetchDiaryDetails(
        @Path(value = "userId") userId: Long,
        @Path(value = "diaryId") diaryId: Long
    ): Response<BaseResponse<DiaryDetailResponse>>

    @Multipart
    @POST("/users/{userId}/diaries/aiCreate")
    suspend fun postImage(
        @Path(value = "userId") userId: Long,
        // todo: 명세서에는 images이지만 image로 변경 건의
        @Part images: MultipartBody.Part
    ): Response<BaseResponse<AIDiaryResponse>>

    @Multipart
    @POST("/users/{userId}/diaries")
    suspend fun postDiary(
        @Path("userId") userId: Long,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: List<MultipartBody.Part>
    ): Response<BaseResponse<DiaryRegistrationResponse>>
}
