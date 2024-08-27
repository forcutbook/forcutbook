package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("code")
    val code: Int,
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: T
)
