package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.data.fixture.UserProfileFixture
import com.fourcutbook.forcutbook.data.mapper.UserProfileMapper.toDomain
import com.fourcutbook.forcutbook.data.mapper.UserStatsMapper.toDomain
import com.fourcutbook.forcutbook.data.service.UserService
import com.fourcutbook.forcutbook.domain.UserProfile
import com.fourcutbook.forcutbook.domain.UserStats
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.io.IOException
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val tokenRepository: TokenRepository,
    private val userService: UserService
) : UserRepository {

    override suspend fun fetchUserStats(userId: Long?): UserStats {
        val myId = tokenRepository.fetchUserId().first()
            ?: throw IllegalStateException("Cannot access on this contents")
        val response = userService.fetchUserStats(
            userId = myId,
            friendId = userId ?: myId
        )

        if (response.isSuccessful) {
            return response.body()
                ?.result
                ?.toDomain()
                ?: throw IOException("Response body is null.")
        }
        throw IOException("Request failed with code ${response.code()}!")
    }

    override suspend fun fetchFollowings(userId: Long?): List<UserProfile> {
        // todo: userId가 null인 경우 -> 나 자신을 조회, null이 아닌 경우 -> 해당 유저를 조회
        val id = userId
            ?: tokenRepository.fetchUserId().firstOrNull()
            ?: throw IllegalStateException("Cannot access on this contents")

        val response = userService.fetchSubscribingDiaries(id)

        if (response.isSuccessful) {
            val subscribingDiaries = response
                .body()
                ?.result
                ?.toDomain()
                ?: throw IOException("Response body is null.")

            return subscribingDiaries
        }
        throw IOException("Request failed with code ${response.code()}!")
    }

    override suspend fun fetchFollowers(userId: Long): List<UserProfile> {
        run {
            return UserProfileFixture.get()
        }
    }

    override suspend fun postFollowingRequest(userId: Long) {
        val myId = tokenRepository
            .fetchUserId()
            .firstOrNull()
            ?: throw IllegalStateException("Cannot access on this contents")
        val response = userService.postSubscribingRequest(
            userId = myId,
            friendId = userId
        )

        if (!response.isSuccessful) throw IOException("Request failed with code ${response.code()}!")
    }

    override suspend fun deleteFollowingRequest(userId: Long) {
        val myId = tokenRepository
            .fetchUserId()
            .firstOrNull()
            ?: throw IllegalStateException("Cannot access on this contents")
        val response = userService.deleteSubscribingRequest(
            userId = myId,
            friendId = userId
        )

        if (!response.isSuccessful) throw IOException("Request failed with code ${response.code()}!")
    }

    override suspend fun deleteFollower(userId: Long) {
        val myId = tokenRepository
            .fetchUserId()
            .firstOrNull()
            ?: throw IllegalStateException("Cannot access on this contents")

        val response = userService.deleteSubscribingDiary(
            userId = myId,
            friendId = userId
        )
        if (!response.isSuccessful) throw IOException("Request failed with code ${response.code()}!")
    }

    override suspend fun deleteSubscribingUser(userId: Long) {
    }

    override suspend fun postAcceptFollowingRequest(userId: Long) {
        val myId = tokenRepository
            .fetchUserId()
            .firstOrNull()
            ?: throw IllegalStateException("Cannot access on this contents")

        val response = userService.postAcceptFollowingRequest(
            userId = myId,
            friendId = userId
        )
        if (!response.isSuccessful) throw IOException("Request failed with code ${response.code()}!")
    }

    override suspend fun postDenyFollowingRequest(userId: Long) {
        val myId = tokenRepository
            .fetchUserId()
            .firstOrNull()
            ?: throw IllegalStateException("Cannot access on this contents")

        val response = userService.postDenyFollowingRequest(
            userId = myId,
            friendId = userId
        )
        if (!response.isSuccessful) throw IOException("Request failed with code ${response.code()}!")
    }
}
