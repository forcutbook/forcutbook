package com.fourcutbook.forcutbook.data.service

import com.forcutbook.forcutbook.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesRetrofit(converterFactory: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
            isLenient = true
        }
        val jsonMediaType = "application/json".toMediaType()

        return json.asConverterFactory(jsonMediaType)
    }

    @Provides
    @Singleton
    fun providesLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun providesDiaryService(retrofit: Retrofit): DiaryService =
        retrofit.create(DiaryService::class.java)

    @Provides
    @Singleton
    fun providesUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun providesNotificationService(retrofit: Retrofit): NotificationService =
        retrofit.create(NotificationService::class.java)
}
