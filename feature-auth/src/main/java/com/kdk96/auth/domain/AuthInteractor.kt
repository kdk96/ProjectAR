package com.kdk96.auth.domain

import io.reactivex.Completable
import javax.inject.Inject

interface AuthInteractor {
    fun checkEmail(email: String): Completable
    fun signIn(email: String, password: String): Completable
    fun signUp(
            email: String,
            name: String,
            password: String,
            passwordConfirmation: String
    ): Completable
}

class AuthInteractorImpl @Inject constructor(
        private val authDataValidator: AuthDataValidator,
        private val authRepository: AuthRepository
) : AuthInteractor {
    override fun checkEmail(email: String) = Completable.fromAction {
        authDataValidator.validateSignInData(email)
    }.andThen(authRepository.checkEmail(email))

    override fun signIn(email: String, password: String) = Completable.fromAction {
        authDataValidator.validateSignInData(email, password)
    }.andThen(authRepository.authorize(email, password))

    override fun signUp(email: String,
                        name: String,
                        password: String,
                        passwordConfirmation: String) = Completable.fromAction {
        authDataValidator.validateSignUpData(email, name, password, passwordConfirmation)
    }.andThen(authRepository.register(email, password, name))
}