package com.kdk96.projectar.auth.data.repository

import com.kdk96.projectar.auth.data.api.AuthApi
import com.kdk96.projectar.auth.domain.AuthErrorMessageProvider
import com.kdk96.projectar.auth.domain.AuthRepository
import com.kdk96.projectar.auth.domain.InvalidCredentialsException
import com.kdk96.projectar.auth.domain.InvalidPasswordException
import com.kdk96.projectar.auth.domain.InvalidUsernameException
import com.kdk96.projectar.auth.domain.NoSuchAccountException
import com.kdk96.projectar.auth.domain.UserDisabledException
import com.kdk96.projectar.common.domain.validation.Valid
import com.kdk96.projectar.common.domain.validation.Verifiable
import com.kdk96.projectar.common.domain.validation.Violation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val authErrorMessageProvider: AuthErrorMessageProvider
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

    override suspend fun signIn(email: String, password: String) {
        try {
            authApi.signIn(email, password)
        } catch (exception: InvalidUsernameException) {
            throw InvalidCredentialsException(exception, emailViolation = Violation(authErrorMessageProvider.invalidEmail))
        } catch (exception: InvalidPasswordException) {
            throw InvalidCredentialsException(exception, passwordViolation = Violation(authErrorMessageProvider.invalidPassword))
        } catch (exception: UserDisabledException) {
            throw InvalidCredentialsException(exception, emailViolation = Violation(authErrorMessageProvider.userDisabled))
        }
    }
}
