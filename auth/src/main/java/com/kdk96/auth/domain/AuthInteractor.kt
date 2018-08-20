package com.kdk96.auth.domain

import com.kdk96.auth.data.repository.AuthRepository
import io.reactivex.Completable
import javax.inject.Inject

class AuthInteractor @Inject constructor(
        private val authDataValidator: AuthDataValidator,
        private val authRepository: AuthRepository
) {
    fun checkEmail(email: String) = Completable.fromAction {
        authDataValidator.validateSignInData(email)
    }.andThen(authRepository.checkEmail(email))

    fun signIn(email: String, password: String) = Completable.fromAction {
        authDataValidator.validateSignInData(email, password)
    }.andThen(authRepository.authorize(email, password))
}