package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.data.mapper.NotificationMapper.toDomain
import com.fourcutbook.forcutbook.data.service.NotificationService
import com.fourcutbook.forcutbook.domain.FriendRequestNotification
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class DefaultNotificationRepository @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val service: NotificationService
) : NotificationRepository {

    override suspend fun fetchFriendRequestNotification(): List<FriendRequestNotification> {
        val userId = tokenRepository.fetchUserId().first()
            ?: throw IllegalStateException("Cannot access on this contents")
        val response = service.fetchFriendRequestNotification(userId)

        if (response.isSuccessful) {
            val notifications = response
                .body()
                ?.result
                ?.toDomain()
                ?: throw IllegalArgumentException("Response body is null")

            return notifications
        }
        throw IOException("Request failed with code ${response.code()}!")
    }
}
