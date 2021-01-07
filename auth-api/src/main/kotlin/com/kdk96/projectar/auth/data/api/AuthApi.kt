package com.kdk96.projectar.auth.data.api

interface AuthApi {
    suspend fun checkEmail(email: String)
//    fun authorize(email: String, password: String): Single<AuthData>
//    fun register(email: String, password: String, name: String): Single<AuthData>
//    fun refresh(refreshToken: String): Single<TokenPair>
}
