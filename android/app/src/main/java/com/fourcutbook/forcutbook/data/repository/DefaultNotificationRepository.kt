package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.data.fixture.NotificationFixture
import com.fourcutbook.forcutbook.data.service.NotificationService
import com.fourcutbook.forcutbook.domain.FriendRequestNotification
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefaultNotificationRepository @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val service: NotificationService
) : NotificationRepository {

    override suspend fun fetchFriendRequestNotification(): List<FriendRequestNotification> {
        val userId = tokenRepository.fetchUserId().first()

        return NotificationFixture.get()
    }
}
