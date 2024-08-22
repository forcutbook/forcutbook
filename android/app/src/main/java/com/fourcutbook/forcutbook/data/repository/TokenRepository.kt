package com.fourcutbook.forcutbook.data.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    val accessToken: Flow<String?>

    val refreshToken: Flow<String?>

    fun fetchUserId(): Flow<Long?>

    suspend fun postUserId(id: Long)

    suspend fun postDelete()

    suspend fun updateAccessToken(accessToken: String)

    suspend fun updateRefreshToken(refreshToken: String)

    suspend fun removeAll()
}
