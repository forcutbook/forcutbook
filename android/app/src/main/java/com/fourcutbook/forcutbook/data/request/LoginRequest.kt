package com.fourcutbook.forcutbook.data.request

import kotlinx.serialization.SerialName

data class LoginRequest(
    @SerialName("username")
    val id: String,
    @SerialName("password")
    val password: String
)
