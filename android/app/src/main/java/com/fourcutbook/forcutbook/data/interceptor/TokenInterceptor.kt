package com.fourcutbook.forcutbook.data.interceptor

import com.fourcutbook.forcutbook.data.repository.TokenRepository
import com.fourcutbook.forcutbook.data.response.TokenResponse
import com.fourcutbook.forcutbook.data.service.AuthService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val authService: AuthService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // 일단 저장된 accessToken을 Header에 넣어준다
        val accessToken = runBlocking { tokenRepository.accessToken.firstOrNull() }
        var response = chain.getResponse(accessToken)
        // todo: count가 무엇인지
        var count = 1

        // Header에 넣어줬는데, 401이 뜬다면, RefreshToken 유무를 확인하고, accessToken을 재발급받아 다시 Header에 넣어준다.
        if (response.code == 401) {
            val refreshToken = runBlocking { tokenRepository.refreshToken.firstOrNull() }
            if (refreshToken != null) {
                while (count <= REQUEST_MAX_NUM) {
                    requestRefreshToken(
                        refreshToken = refreshToken,
                        onSuccess = { data ->
                            tokenRepository.updateAccessToken(data.accessToken)
                            tokenRepository.updateRefreshToken(data.refreshToken)
                            response = chain.getResponse(data.accessToken)
                            count = 4
                        },
                        onFail = { count++ }
                    )
                }
            }
        }
        return response
    }

    private fun requestRefreshToken(
        refreshToken: String,
        onSuccess: suspend (TokenResponse) -> Unit,
        onFail: () -> Unit
    ) {
        runBlocking {
            val response = authService.refreshAccessToken(TOKEN_FORMAT.format(refreshToken))

            if (response.isSuccessful) {
                response.body()?.let { tokenResponse ->
                    onSuccess(tokenResponse.result)
                }
            } else {
                onFail()
            }
        }
    }

    private fun Interceptor.Chain.getResponse(accessToken: String?): Response {
        return this
            .proceed(
                request()
                    .newBuilder()
                    .addHeader(
                        ACCESS_TOKEN_HEADER,
                        TOKEN_FORMAT.format(accessToken)
                    ).build()
            )
    }

    companion object {
        private const val ACCESS_TOKEN_HEADER = "Authorization"
        private const val TOKEN_FORMAT = "Bearer %s"
        private const val REQUEST_MAX_NUM = 3
    }
}
