package com.fourcutbook.forcutbook.domain

data class UserStats(
    val nickname: String,
    val userId: Long,
    val subscribingDiaryCount: Int,
    val subscribingUserCount: Int,
    val diaryCount: Int,
    val subscribingStatus: SubscribingStatus
)
