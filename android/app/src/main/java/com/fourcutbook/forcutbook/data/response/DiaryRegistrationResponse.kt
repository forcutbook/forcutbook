package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryRegistrationResponse(
    @SerialName("id")
    val diaryId: Long
)
