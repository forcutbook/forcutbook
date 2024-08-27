package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscribingAcceptOrDenyResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("status")
    val status: String
)
