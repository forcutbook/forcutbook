package com.fourcutbook.forcutbook.data.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteFollowerFollowingResponse(
    @SerialName("id")
    val id: Long
)
