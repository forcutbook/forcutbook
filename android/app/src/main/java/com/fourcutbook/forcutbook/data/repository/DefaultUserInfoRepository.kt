package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.data.mapper.UserProfileMapper.toDomain
import com.fourcutbook.forcutbook.data.response.UserInfoResponse
import com.fourcutbook.forcutbook.data.response.UserProfileResponse
import com.fourcutbook.forcutbook.domain.SubscribingCount
import com.fourcutbook.forcutbook.domain.UserProfile
import javax.inject.Inject

class DefaultUserInfoRepository @Inject constructor(
    private val diaryRepository: DiaryRepository
) : UserInfoRepository {

    override suspend fun fetchSubscribingCount(): SubscribingCount {
        run {
            val userInfoResponse = UserInfoResponse(
                numberOfSubscribingDiary = 32,
                numberOfSubscribingUser = 32,
                nickName = "",
                userId = 1944,
                imageUrl = "",
                diaries = listOf()
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

    override suspend fun fetchSubscribingDiaries(userId: Long): List<UserProfile> {
        run {
            val subscribingDiariesResponse = UserProfilesResponse(
                profiles = listOf(
                    UserProfileResponse(
                        profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
                        nickname = "woogi"
                    ),
                    UserProfileResponse(
                        profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
                        nickname = "woogi"
                    ),
                    UserProfileResponse(
                        profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
                        nickname = "woogi"
                    ),
                    UserProfileResponse(
                        profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
                        nickname = "woogi"
                    )
                )
            )

//            if (!userInfoResponse.isSuccessful()) {
//
//            }
//            throw IOException("Request failed with code ${response.code()}!")
            return subscribingDiariesResponse.profiles.map { it.toDomain() }
        }
    }

    override suspend fun fetchSubscribedUsers(userId: Long): List<UserProfile> {
        run {
            val subscribedUserResponse = UserProfilesResponse(
                profiles = listOf(
                    UserProfileResponse(
                        profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
                        nickname = "woogi"
                    ),
                    UserProfileResponse(
                        profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
                        nickname = "woogi"
                    ),
                    UserProfileResponse(
                        profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
                        nickname = "woogi"
                    ),
                    UserProfileResponse(
                        profileImageUrl = "https://forcutbook-diary-images.s3.ap-northeast-2.amazonaws.com/d284c5b5-e80c-49e5-9ff5-f60d0fef685d.jpg",
                        nickname = "woogi"
                    )
                )
            )
//            if (!userInfoResponse.isSuccessful()) {
//
//            }
//            throw IOException("Request failed with code ${response.code()}!")
            return subscribedUserResponse.profiles.map { it.toDomain() }
        }
    }

    override suspend fun postSubscribeDiary(userId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun postCancelSubscribingDiary(userId: Long) {
        TODO("Not yet implemented")
    }
}
