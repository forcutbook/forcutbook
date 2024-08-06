package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.domain.SubscribingCount
import com.fourcutbook.forcutbook.domain.UserProfile

interface UserInfoRepository {

    suspend fun fetchSubscribingCount(): SubscribingCount

    suspend fun fetchSubscribingDiaries(userId: Long): List<UserProfile>

    suspend fun fetchSubscribedUsers(userId: Long): List<UserProfile>

    suspend fun postSubscribeDiary(userId: Long)

    suspend fun postCancelSubscribingDiary(userId: Long)
}
