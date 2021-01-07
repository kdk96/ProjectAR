package com.kdk96.projectar.auth.data.repository

import com.kdk96.projectar.auth.data.api.AuthApi
import com.kdk96.projectar.auth.domain.AuthErrorMessageProvider
import com.kdk96.projectar.auth.domain.AuthRepository
import com.kdk96.projectar.auth.domain.InvalidUsernameException
import com.kdk96.projectar.auth.domain.NoSuchAccountException
import com.kdk96.projectar.common.domain.validation.Valid
import com.kdk96.projectar.common.domain.validation.Verifiable
import com.kdk96.projectar.common.domain.validation.Violation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val authErrorMessageProvider: AuthErrorMessageProvider
//        @Rx.Io private val ioScheduler: Scheduler,
//        private val authHolder: AuthHolder
) : AuthRepository {

    override fun checkEmail(email: String): Flow<Verifiable> = flow {
        try {
            authApi.checkEmail(email)
            emit(Valid)
        } catch (e: InvalidUsernameException) {
            emit(Violation(authErrorMessageProvider.invalidEmail))
        } catch (e: NoSuchAccountException) {
            emit(Violation(authErrorMessageProvider.noSuchAccount))
        }
    }
//
//    override fun authorize(
//            email: String,
//            password: String
//    ) = authApi.authorize(email, password)
//            .doOnSuccess { authHolder.saveAuthData(it) }
//            .subscribeOn(ioScheduler).ignoreElement()
//
//    override fun register(
//            email: String,
//            password: String,
//            name: String
//    ) = authApi.register(email, password, name)
//            .doOnSuccess { authHolder.saveAuthData(it) }
//            .subscribeOn(ioScheduler).ignoreElement()
}
