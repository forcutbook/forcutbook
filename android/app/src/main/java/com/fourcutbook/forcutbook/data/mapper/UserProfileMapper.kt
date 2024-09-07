package com.fourcutbook.forcutbook.data.mapper

import com.fourcutbook.forcutbook.data.response.FollowerListResponse
import com.fourcutbook.forcutbook.data.response.FollowingListResponse
import com.fourcutbook.forcutbook.data.response.UserProfileResponse
import com.fourcutbook.forcutbook.domain.FollowingStatus
import com.fourcutbook.forcutbook.domain.UserProfile

object UserProfileMapper {

    private const val SUBSCRIBING = "구독중"
    private const val REQUESTING = "요청중"
    private const val NONE = "구독"
    private const val CANNOT_FIND_OUT = "구독 상태를 알 수 없습니다."

    fun FollowingListResponse.toDomain(): List<UserProfile> =
        followings.map { userProfile ->
            userProfile.toDomain()
        }

    fun FollowerListResponse.toDomain(): List<UserProfile> =
        followers.map { userProfile ->
            userProfile.toDomain()
        }

    fun UserProfileResponse.toDomain(): UserProfile = UserProfile(
        profileImageUrl = profileImageUrl,
        nickname = nickname,
        userId = userId,
        followingStatus = isSubscribing.toSubscribingStatus()
    )

    private fun String.toSubscribingStatus(): FollowingStatus = when (this) {
        SUBSCRIBING -> FollowingStatus.SUBSCRIBED
        REQUESTING -> FollowingStatus.REQUESTED
        NONE -> FollowingStatus.NONE
        else -> throw IllegalArgumentException(CANNOT_FIND_OUT)
    }
}
