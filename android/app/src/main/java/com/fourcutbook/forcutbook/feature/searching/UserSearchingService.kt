package com.fourcutbook.forcutbook.feature.searching

import com.fourcutbook.forcutbook.data.response.UserProfilesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserSearchingService {

    @GET("/users/{userId}")
    suspend fun fetchUsers(
        @Path(value = "userId") userId: Long,
        @Query("search") userName: String
    ): Response<UserProfilesResponse>
}
