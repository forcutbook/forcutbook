package com.fourcutbook.forcutbook.data.response

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryResponse(
    @SerialName("diaryId")
    val diaryId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("createdAt")
    val date: LocalDateTime
)
