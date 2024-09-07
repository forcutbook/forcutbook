package com.fourcutbook.forcutbook.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsLoginRepository(loginRepository: DefaultLoginRepository): LoginRepository

    @Binds
    @Singleton
    fun bindsDiaryRepository(diaryRepository: DefaultDiaryRepository): DiaryRepository

    @Binds
    @Singleton
    fun bindsUserRepository(userRepository: DefaultTokenRepository): TokenRepository

    @Binds
    @Singleton
    fun bindsUserInfoRepository(userInfoRepository: DefaultUserRepository): UserRepository

    @Binds
    @Singleton
    fun bindsNotificationRepository(notificationRepository: DefaultNotificationRepository): NotificationRepository

    @Binds
    @Singleton
    fun bindsUserSearchingRepository(userSearchingRepository: DefaultUserSearchingRepository): UserSearchingRepository
}
