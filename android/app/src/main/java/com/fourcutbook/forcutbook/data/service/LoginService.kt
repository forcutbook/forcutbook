package com.fourcutbook.forcutbook.data.service

import com.fourcutbook.forcutbook.data.request.LoginRequest
import com.fourcutbook.forcutbook.data.response.BaseResponse
import com.fourcutbook.forcutbook.data.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/auth/login")
    suspend fun postLogin(
        @Body loginRequest: LoginRequest
    ): Response<BaseResponse<LoginResponse>>
}
