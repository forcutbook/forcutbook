package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiariesResponse(
    @SerialName("userId")
    val userId: String,
    @SerialName("keyword")
    val keyword: String,
    @SerialName("createdAt")
    val diaries: List<DiaryResponse>
)
