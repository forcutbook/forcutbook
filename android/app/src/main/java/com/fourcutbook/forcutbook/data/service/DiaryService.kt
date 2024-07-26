package com.fourcutbook.forcutbook.data.service

import com.fourcutbook.forcutbook.data.response.DiariesResponse
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

    @GET("/users/{userId}/diaries")
    suspend fun fetchDiaries(
        @Path(value = "userId") userId: Long
    ): Response<DiariesResponse>

    @Multipart
    @POST("/users/{userId}/diaries/aiCreate")
    suspend fun postImage(
        @Path(value = "userId") userId: Long,
        // todo: 명세서에는 images이지만 image로 변경 건의
        @Part image: List<MultipartBody.Part>
    ): Response<AIDiaryResponse>

    @Multipart
    @POST("/users/{userId}/diaries")
    suspend fun postDiary(
        @Path("userId") userId: Long,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: List<MultipartBody.Part>,
        @Part("friends") friends: RequestBody
    ): Response<DiaryRegistrationResponse>
}
