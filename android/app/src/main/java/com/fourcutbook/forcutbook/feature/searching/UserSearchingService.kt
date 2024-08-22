package com.fourcutbook.forcutbook.feature.searching

import com.fourcutbook.forcutbook.data.response.UserSearchingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserSearchingService {

    @GET("/users")
    suspend fun fetchUsers(
        @Query("search") userName: String
    ): Response<UserSearchingResponse>
}
