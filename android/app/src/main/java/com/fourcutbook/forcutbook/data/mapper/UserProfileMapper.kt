package com.fourcutbook.forcutbook.data.mapper

import com.fourcutbook.forcutbook.data.response.UserProfileResponse
import com.fourcutbook.forcutbook.data.response.UserSearchingResponse
import com.fourcutbook.forcutbook.domain.SubscribingStatus
import com.fourcutbook.forcutbook.domain.UserProfile

object UserProfileMapper {

    private const val SUBSCRIBING = "구독중"
    private const val REQUESTING = "요청중"
    private const val NONE = "구독"
    private const val CANNOT_FIND_OUT = "구독 상태를 알 수 없습니다."

    fun UserSearchingResponse.toDomain(): List<UserProfile> = users.map { it.toDomain() }

    fun UserProfileResponse.toDomain(): UserProfile = UserProfile(
        profileImageUrl = profileImageUrl,
        nickname = nickname,
        userId = userId,
        isSubscribing = isSubscribing.toSubscribingStatus()
    )

    private fun String.toSubscribingStatus(): SubscribingStatus = when (this) {
        SUBSCRIBING -> SubscribingStatus.SUBSCRIBED
        REQUESTING -> SubscribingStatus.REQUESTED
        NONE -> SubscribingStatus.NONE
        else -> throw IllegalArgumentException(CANNOT_FIND_OUT)
    }
}
