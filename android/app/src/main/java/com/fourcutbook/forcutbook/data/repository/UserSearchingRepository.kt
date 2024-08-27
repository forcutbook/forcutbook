package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.domain.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserSearchingRepository {

    suspend fun fetchUsers(userName: String): Flow<List<UserProfile>>
}
