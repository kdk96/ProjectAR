package com.kdk96.auth.domain

import com.kdk96.auth.data.repository.AuthRepository
import io.reactivex.Completable

class AuthInteractor(
        private val authDataValidator: AuthDataValidator,
        private val authRepository: AuthRepository
) {
    fun checkEmail(email: String) = Completable.fromAction {
        authDataValidator.validateSignInData(email)
    }.andThen(authRepository.checkEmail(email))

    fun signIn(email: String, password: String) = Completable.fromAction {
        authDataValidator.validateSignInData(email, password)
    }.andThen(authRepository.authorize(email, password))

    fun signUp(email: String,
               name: String,
               password: String,
               passwordConfirmation: String) = Completable.fromAction {
        authDataValidator.validateSignUpData(email, name, password, passwordConfirmation)
    }.andThen(authRepository.register(email, password, name))
}