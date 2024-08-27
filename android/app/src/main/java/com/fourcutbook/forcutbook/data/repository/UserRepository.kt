package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.domain.UserProfile
import com.fourcutbook.forcutbook.domain.UserStats

interface UserRepository {

    suspend fun fetchUserStats(userId: Long? = null): UserStats

    suspend fun fetchFollowings(userId: Long? = null): List<UserProfile>

    suspend fun fetchFollowers(userId: Long? = null): List<UserProfile>

    suspend fun postFollowingRequest(userId: Long)

    suspend fun deleteFollowingRequest(userId: Long)

    suspend fun deleteFollowing(userId: Long)

    suspend fun postCancelFollower(userId: Long)

    suspend fun postAcceptFollowingRequest(userId: Long)

    suspend fun postDenyFollowingRequest(userId: Long)

    suspend fun deleteFollower(friendId: Long)
}
