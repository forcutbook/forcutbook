package com.fourcutbook.forcutbook.data.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AIDiaryResponse(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String
)
