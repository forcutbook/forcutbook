package com.fourcutbook.forcutbook.data.repository

import javax.inject.Inject

class DefaultLoginRepository @Inject constructor() : LoginRepository {

    override suspend fun postLogin(id: String, password: String) {
    }
}
