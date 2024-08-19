package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.data.fixture.NotificationFixture
import com.fourcutbook.forcutbook.data.service.NotificationService
import com.fourcutbook.forcutbook.domain.FriendRequestNotification
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefaultNotificationRepository @Inject constructor(
    private val userRepository: UserRepository,
    private val service: NotificationService
) : NotificationRepository {

    override suspend fun fetchFriendRequestNotification(): List<FriendRequestNotification> {
        val userId = userRepository.fetchUserId().first()

        return NotificationFixture.get()
    }
}
