package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.data.response.UserInfoResponse
import com.fourcutbook.forcutbook.domain.SubscribingCount
import javax.inject.Inject

class DefaultUserInfoRepository @Inject constructor(
    private val diaryRepository: DiaryRepository
) : UserInfoRepository {

    override suspend fun fetchSubscribingCount(): SubscribingCount {
        run {
            val userInfoResponse = UserInfoResponse(
                numberOfSubscribingDiary = 32,
                numberOfSubscribingUser = 32
            )

//            if (!userInfoResponse.isSuccessful()) {
//
//            }
//            throw IOException("Request failed with code ${response.code()}!")
            return SubscribingCount(
                subscribingDiaryCount = userInfoResponse.numberOfSubscribingDiary,
                subscribingUserCount = userInfoResponse.numberOfSubscribingUser
            )
        }
    }
}
