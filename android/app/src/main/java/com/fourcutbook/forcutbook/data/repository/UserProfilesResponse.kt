package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.data.response.UserProfileResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfilesResponse(
    @SerialName("profiles")
    val profiles: List<UserProfileResponse>
)
