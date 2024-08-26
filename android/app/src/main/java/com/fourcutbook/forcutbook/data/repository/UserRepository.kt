package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.domain.UserProfile
import com.fourcutbook.forcutbook.domain.UserStats

interface UserRepository {

    suspend fun fetchUserStats(userId: Long? = null): UserStats

    suspend fun fetchFollowings(userId: Long? = null): List<UserProfile>

    suspend fun fetchFollowers(userId: Long): List<UserProfile>

    suspend fun postFollowingRequest(userId: Long)

    suspend fun deleteFollowingRequest(userId: Long)

    suspend fun deleteFollower(userId: Long)

    suspend fun deleteSubscribingUser(userId: Long)
}
