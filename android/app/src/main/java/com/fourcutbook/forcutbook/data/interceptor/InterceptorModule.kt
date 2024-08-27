package com.fourcutbook.forcutbook.data.interceptor

import com.fourcutbook.forcutbook.data.repository.TokenRepository
import com.fourcutbook.forcutbook.data.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    @Provides
    @Singleton
    fun providesHeaderInterceptor(
        tokenRepository: TokenRepository,
        authService: AuthService
    ): Interceptor = TokenInterceptor(tokenRepository, authService)
}
