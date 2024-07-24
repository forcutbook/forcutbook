package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiariesResponse(
    @SerialName("diaries")
    val diaries: List<DiaryResponse>
)
