package com.fourcutbook.forcutbook.data.mapper

import com.fourcutbook.forcutbook.data.response.UserStatsResponse
import com.fourcutbook.forcutbook.domain.FollowingStatus
import com.fourcutbook.forcutbook.domain.UserStats

object UserStatsMapper {

    private const val SUBSCRIBING = "구독중"
    private const val REQUESTING = "요청중"
    private const val NONE = "구독"
    private const val CANNOT_FIND_OUT = "구독 상태를 알 수 없습니다."

    fun UserStatsResponse.toDomain(): UserStats = UserStats(
        nickname = nickname,
        userId = userId,
        subscribingDiaryCount = subscribingDiaryCount,
        subscribingUserCount = subscribingUserCount,
        diaryCount = diaryCount,
        followingStatus = subscribingStatus.toSubscribingStatus()
    )

    private fun String.toSubscribingStatus(): FollowingStatus = when (this) {
        SUBSCRIBING -> FollowingStatus.SUBSCRIBED
        REQUESTING -> FollowingStatus.REQUESTED
        NONE -> FollowingStatus.NONE
        else -> throw IllegalArgumentException(CANNOT_FIND_OUT)
    }
}
