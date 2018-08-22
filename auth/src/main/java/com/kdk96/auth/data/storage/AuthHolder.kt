package com.kdk96.auth.data.storage

import com.kdk96.auth.data.entity.AuthData
import com.kdk96.auth.data.repository.AuthApi
import javax.inject.Inject

class AuthHolder @Inject constructor(private val prefs: Prefs, private val authApi: AuthApi) {
    var accessToken: String? = prefs.getAccessToken()

    fun saveAuthData(authData: AuthData) {
        with(authData) { prefs.saveAuthData(accessToken, refreshToken, email, name) }
        accessToken = authData.accessToken
    }

    fun refresh() = authApi.refresh(prefs.getRefreshToken())
            .doOnSuccess { prefs.saveTokenPair(it.accessToken, it.refreshToken) }
            .blockingGet()
}