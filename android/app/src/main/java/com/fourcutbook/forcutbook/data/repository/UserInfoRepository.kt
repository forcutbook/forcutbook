package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.domain.SubscribingCount

interface UserInfoRepository {

    suspend fun fetchSubscribingCount(): SubscribingCount
}
