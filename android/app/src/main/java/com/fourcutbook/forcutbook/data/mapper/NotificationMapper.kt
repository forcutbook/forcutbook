package com.fourcutbook.forcutbook.data.mapper

import com.fourcutbook.forcutbook.data.response.FriendRequestNotificationsResponse
import com.fourcutbook.forcutbook.domain.FriendRequestNotification

object NotificationMapper {

    fun FriendRequestNotificationsResponse.toDomain(): List<FriendRequestNotification> =
        notifications.map { friendRequestNotification ->
            FriendRequestNotification(
                userId = friendRequestNotification.userId,
                profileImgUrl = friendRequestNotification.profileImgUrl,
                // todo: nickname 생길 시 변경
                userNickname = friendRequestNotification.userId.toString()
            )
        }
}
