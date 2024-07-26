package com.fourcutbook.forcutbook.data.response

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryDetailResponse(
    @SerialName("diaryId")
    val diaryId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val contents: String,
    @SerialName("images")
    val images: List<ImageResponse>,
    @SerialName("createdAt")
    val date: LocalDateTime
)
