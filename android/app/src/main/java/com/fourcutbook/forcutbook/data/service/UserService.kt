package com.fourcutbook.forcutbook.data.service

import com.fourcutbook.forcutbook.data.response.DiaryDetailResponse
import retrofit2.Response

interface UserService {

//    @GET("/users/{userId}/diaries/{diaryId}")
    suspend fun fetchDiaryDetails(
//        @Path(value = "userId") userId: Long,
//        @Path(value = "diaryId") diaryId: Long
    ): Response<DiaryDetailResponse>
}
