package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.data.fixture.UserProfileFixture
import com.fourcutbook.forcutbook.domain.UserProfile
import com.fourcutbook.forcutbook.feature.searching.UserSearchingService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultUserSearchingRepository @Inject constructor(
    private val userSearchingService: UserSearchingService
) : UserSearchingRepository {

    override suspend fun fetchUsers(userName: String): Flow<List<UserProfile>> = flow {
//        run{
// //            val response = userSearchingService.fetchUsers(userName)
// //            Log.d("woogi", "fetchUsers: $response")
// //
// //            if (response.isSuccessful) {
// //                val userProfiles = response.body()
// //
// //                userProfiles?.toDomain() ?: throw IOException("Response body is null.")
// //            }
// //            throw IOException("Request failed with code ${response.code()}!")
//            Log.d("woogi", "요청보냄ㅅ")
        emit(UserProfileFixture.get())
//        }
    }
}
