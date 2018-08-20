package com.kdk96.auth.data.repository

import com.kdk96.common.di.Rx
import io.reactivex.Scheduler
import javax.inject.Inject

class AuthRepository @Inject constructor(
        private val authApi: AuthApi,
        @Rx.Io private val ioScheduler: Scheduler
) {
    fun checkEmail(email: String) = authApi.checkEmail(email).subscribeOn(ioScheduler)

    fun authorize(email: String, password: String) =
            authApi.authorize(email, password).subscribeOn(ioScheduler)

    fun register(email: String, password: String, name: String) =
            authApi.register(email, password, name).subscribeOn(ioScheduler)
}