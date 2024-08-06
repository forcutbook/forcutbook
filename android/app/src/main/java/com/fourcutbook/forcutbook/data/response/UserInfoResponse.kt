package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("nickName")
    val nickName: String,
    @SerialName("userId")
    val userId: Long,
    @SerialName("profileImageUrl")
    val imageUrl: String,
    @SerialName("diaries")
    val diaries: List<DiaryResponse>,
    @SerialName("p1")
    val numberOfSubscribingDiary: Int,
    @SerialName("p2")
    val numberOfSubscribingUser: Int
)
