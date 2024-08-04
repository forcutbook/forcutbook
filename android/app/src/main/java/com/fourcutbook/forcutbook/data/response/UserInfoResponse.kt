package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("p1")
    val numberOfSubscribingDiary: Int,
    @SerialName("p2")
    val numberOfSubscribingUser: Int
)
