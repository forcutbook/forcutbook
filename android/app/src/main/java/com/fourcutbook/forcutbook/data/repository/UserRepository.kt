package com.fourcutbook.forcutbook.data.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    // todo: client에서 userId를 계속 가지고 있어야한다..?
    fun fetchUserId(): Flow<Long>

    suspend fun postUserId(id: Long)

    suspend fun postDelete()
}
