package com.kdk96.auth.data.storage

import com.kdk96.auth.data.entity.AuthData
import com.kdk96.auth.data.entity.TokenPair
import com.kdk96.auth.data.repository.AuthApi
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.HttpException

typealias OnRefreshFailureListener = () -> Unit

class AuthHolder(private val prefs: Prefs, private val authApi: AuthApi) {
    var accessToken: String? = prefs.getAccessToken()

    fun saveAuthData(authData: AuthData) {
        accessToken = authData.accessToken
        with(authData) { prefs.saveAuthData(accessToken, refreshToken, email, name) }
    }

    private lateinit var refreshFailureListener: OnRefreshFailureListener

    fun registerRefreshFailureListener(refreshFailureListener: OnRefreshFailureListener) {
        this.refreshFailureListener = refreshFailureListener
    }

    fun refresh() = authApi.refresh(prefs.getRefreshToken())
            .doOnSuccess {
                prefs.saveTokenPair(it.accessToken, it.refreshToken)
                accessToken = it.accessToken
            }.onErrorResumeNext(::onRefreshError)
            .ignoreElement()
            .blockingAwait()


    private fun onRefreshError(throwable: Throwable): Single<TokenPair> {
        accessToken = null
        return if (throwable is HttpException && throwable.code() == 400 &&
                isTokenExpiredErrorBody(throwable.response().errorBody())) {
            refreshFailureListener.invoke()
            Single.just(TokenPair("", ""))
        } else Single.error(throwable)
    }

    private fun isTokenExpiredErrorBody(errorBody: ResponseBody?): Boolean {
        return errorBody?.string()?.contains("TOKEN_EXPIRED") ?: false
    }
}