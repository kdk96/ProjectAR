package com.kdk96.projectar.auth.data.api

import com.kdk96.projectar.auth.data.entity.TokenPair

interface AuthApi {
    suspend fun checkEmail(email: String)
    suspend fun signIn(email: String, password: String): TokenPair
//    fun register(email: String, password: String, name: String): Single<AuthData>
//    fun refresh(refreshToken: String): Single<TokenPair>
}
