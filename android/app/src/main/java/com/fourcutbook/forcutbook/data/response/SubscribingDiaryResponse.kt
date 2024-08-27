package com.fourcutbook.forcutbook.data.response

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscribingDiaryResponse(
    @SerialName("userId")
    val userId: Long,
    @SerialName("userName")
    val nickname: String,
//    @SerialName("profileImageUrl")
//    val profileImageUrl: String,
    @SerialName("createdAt")
    val createdAt: LocalDateTime
)
