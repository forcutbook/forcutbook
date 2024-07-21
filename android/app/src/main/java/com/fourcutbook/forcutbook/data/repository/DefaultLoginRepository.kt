package com.fourcutbook.forcutbook.data.repository

import com.fourcutbook.forcutbook.data.request.LoginRequest
import com.fourcutbook.forcutbook.data.service.LoginService
import java.io.IOException
import javax.inject.Inject

class DefaultLoginRepository @Inject constructor(
    private val loginService: LoginService
) : LoginRepository {

    override suspend fun postLogin(id: String, password: String) {
        return run {
            val response = loginService.postLogin(
                loginRequest = LoginRequest(
                    id = id,
                    password = password
                )
            )

            if (!response.isSuccessful) {
                throw IOException("request failed with code: ${response.code()}")
            }
        }
    }
}
