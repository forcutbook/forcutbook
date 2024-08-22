package com.fourcutbook.forcutbook.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSearchingResponse(
    @SerialName("data")
    val users: List<UserProfileResponse>
)
