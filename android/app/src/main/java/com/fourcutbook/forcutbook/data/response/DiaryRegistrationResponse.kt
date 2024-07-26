package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryRegistrationResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String
)
