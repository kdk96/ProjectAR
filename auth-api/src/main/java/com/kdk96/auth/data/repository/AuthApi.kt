package com.kdk96.auth.data.repository

import com.kdk96.auth.data.entity.AuthData
import com.kdk96.auth.data.entity.TokenPair
import io.reactivex.Completable
import io.reactivex.Single

interface AuthApi {
    fun checkEmail(email: String): Completable
    fun authorize(email: String, password: String): Single<AuthData>
    fun register(email: String, password: String, name: String): Single<AuthData>
    fun refresh(refreshToken: String): Single<TokenPair>
}