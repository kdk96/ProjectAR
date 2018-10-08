package com.kdk96.auth.data.repository

import com.kdk96.auth.data.storage.AuthHolder
import io.reactivex.Scheduler

class AuthRepository(
        private val authApi: AuthApi,
        private val ioScheduler: Scheduler,
        private val authHolder: AuthHolder
) {
    fun checkEmail(email: String) = authApi.checkEmail(email).subscribeOn(ioScheduler)

    fun authorize(email: String, password: String) = authApi.authorize(email, password)
            .doOnSuccess { authHolder.saveAuthData(it) }
            .subscribeOn(ioScheduler).ignoreElement()

    fun register(email: String, password: String, name: String) = authApi.register(email, password, name)
            .doOnSuccess { authHolder.saveAuthData(it) }
            .subscribeOn(ioScheduler).ignoreElement()
}