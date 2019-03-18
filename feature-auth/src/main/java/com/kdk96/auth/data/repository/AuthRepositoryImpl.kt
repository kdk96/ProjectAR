package com.kdk96.auth.data.repository

import com.kdk96.auth.data.storage.AuthHolder
import com.kdk96.auth.domain.AuthRepository
import com.kdk96.common.di.Rx
import io.reactivex.Scheduler
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
        private val authApi: AuthApi,
        @Rx.Io private val ioScheduler: Scheduler,
        private val authHolder: AuthHolder
) : AuthRepository {
    override fun checkEmail(email: String) = authApi.checkEmail(email).subscribeOn(ioScheduler)

    override fun authorize(
            email: String,
            password: String
    ) = authApi.authorize(email, password)
            .doOnSuccess { authHolder.saveAuthData(it) }
            .subscribeOn(ioScheduler).ignoreElement()

    override fun register(
            email: String,
            password: String,
            name: String
    ) = authApi.register(email, password, name)
            .doOnSuccess { authHolder.saveAuthData(it) }
            .subscribeOn(ioScheduler).ignoreElement()
}