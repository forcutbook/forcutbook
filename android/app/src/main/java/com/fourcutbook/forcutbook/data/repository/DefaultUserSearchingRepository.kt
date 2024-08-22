package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.data.mapper.UserProfileMapper.toDomain
import com.fourcutbook.forcutbook.domain.UserProfile
import com.fourcutbook.forcutbook.feature.searching.UserSearchingService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class DefaultUserSearchingRepository @Inject constructor(
    private val userSearchingService: UserSearchingService,
    private val tokenRepository: TokenRepository
) : UserSearchingRepository {

    override suspend fun fetchUsers(userName: String): Flow<List<UserProfile>> = flow {
        val userId = tokenRepository
            .fetchUserId()
            .firstOrNull()
            ?: throw IllegalStateException("Cannot access on this contents")
        val response = userSearchingService.fetchUsers(
            userId = userId,
            userName = userName
        )
        if (!response.isSuccessful) throw IOException("Request failed with code ${response.code()}!")
        val userProfiles = response
            .body()
            ?.profiles
            ?.map { profileResponse ->
                profileResponse.toDomain()
            } ?: throw IOException("Response body is empty.")

        emit(userProfiles)
    }
}
