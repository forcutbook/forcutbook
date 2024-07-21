package com.fourcutbook.forcutbook.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("userName")
    val id: String,
    @SerialName("password")
    val password: String
)
