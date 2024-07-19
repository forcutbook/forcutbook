package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName

data class LoginResponse(
    @SerialName("code")
    val code: Int,
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("userId")
    val userId: Int
)
