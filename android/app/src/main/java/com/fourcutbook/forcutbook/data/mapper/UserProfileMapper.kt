package com.fourcutbook.forcutbook.data.mapper

import com.fourcutbook.forcutbook.data.response.UserProfileResponse
import com.fourcutbook.forcutbook.domain.UserProfile

object UserProfileMapper {

    fun UserProfileResponse.toDomain(): UserProfile = UserProfile(
        profileImageUrl = profileImageUrl,
        nickname = nickname
    )
}
