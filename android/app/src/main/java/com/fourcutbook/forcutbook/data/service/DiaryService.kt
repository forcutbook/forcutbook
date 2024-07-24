package com.fourcutbook.forcutbook.data.service

import com.fourcutbook.forcutbook.data.response.DiariesResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.time.LocalDateTime

interface DiaryService {

    @GET("/users/{userId}/diaries")
    suspend fun fetchDiaries(
        @Path(value = "userId") userId: Long
    ): Response<DiariesResponse>

    @GET
    @Multipart
    @POST("/users/{userId}/diaries/aiCreate")
    suspend fun createAIDiaries(
        @Path(value = "userId") userId: Int,
        // todo: 명세서에는 images이지만 image로 변경 건의
        @Part("image") image: MultipartBody.Part,
        @Part("title") title: String,
        @Part("content") content: String,
        @Part("dateTime") dateTime: LocalDateTime
    )

    @Multipart
    @POST("/users/{userId}/diaries")
    suspend fun test(
        @Part image: MultipartBody.Part,
        @Path("userId") userId: Int,
        @Part("content") content: String
    )
}
