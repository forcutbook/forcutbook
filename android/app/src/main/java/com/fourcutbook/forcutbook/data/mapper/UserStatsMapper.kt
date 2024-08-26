package com.fourcutbook.forcutbook.data.mapper

import com.fourcutbook.forcutbook.data.response.UserStatsResponse
import com.fourcutbook.forcutbook.domain.SubscribingStatus
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
        subscribingStatus = subscribingStatus.toSubscribingStatus()
    )

    private fun String.toSubscribingStatus(): SubscribingStatus = when (this) {
        SUBSCRIBING -> SubscribingStatus.SUBSCRIBED
        REQUESTING -> SubscribingStatus.REQUESTED
        NONE -> SubscribingStatus.NONE
        else -> throw IllegalArgumentException(CANNOT_FIND_OUT)
    }
}
