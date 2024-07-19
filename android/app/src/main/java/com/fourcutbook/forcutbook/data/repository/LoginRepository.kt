package com.fourcutbook.forcutbook.data.repository

interface LoginRepository {

    suspend fun postLogin(id: String, password: String)
}
