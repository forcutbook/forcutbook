package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserStatsResponse(
    @SerialName("userId")
    val userId: Long,
    @SerialName("userName")
    val nickname: String,
    @SerialName("subscriptionCnt")
    val subscribingDiaryCount: Int,
    @SerialName("followerCnt")
    val subscribingUserCount: Int,
    @SerialName("diaryCnt")
    val diaryCount: Int,
    @SerialName("friendRequestStatus")
    val subscribingStatus: String
)
