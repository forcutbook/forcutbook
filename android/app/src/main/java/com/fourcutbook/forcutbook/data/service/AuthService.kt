package com.fourcutbook.forcutbook.data.service

import com.fourcutbook.forcutbook.data.response.LoginResponse
import com.fourcutbook.forcutbook.data.response.TokenResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/kakao/login")
    suspend fun kakaoLogin(
        @Header("Authorization")
        idToken: String
    ): Response<LoginResponse>

    @POST("/auth/kakao/refresh")
    suspend fun refreshAccessToken(
        @Header("Authorization") refreshToken: String
    ): Response<TokenResponse>

    @POST("/auth/logout")
    suspend fun logout(): Response<String>
}
