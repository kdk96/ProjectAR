package com.kdk96.projectar.auth.data.api

import com.kdk96.projectar.auth.data.entity.FetchProvidersRequestBody
import com.kdk96.projectar.auth.data.entity.FetchProvidersResponse
import com.kdk96.projectar.auth.data.entity.SignInRequestBody
import com.kdk96.projectar.auth.data.entity.SignInResponse
import com.kdk96.projectar.auth.data.entity.TokenPair
import com.kdk96.projectar.auth.data.entity.toTokenPair
import com.kdk96.projectar.auth.domain.InvalidPasswordException
import com.kdk96.projectar.auth.domain.InvalidUsernameException
import com.kdk96.projectar.auth.domain.NoSuchAccountException
import com.kdk96.projectar.auth.domain.UserDisabledException
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.post
import io.ktor.client.statement.readText

class FirebaseAuthApi(
    private val httpClient: HttpClient
) : AuthApi {

    override suspend fun checkEmail(email: String) {
        try {
            val response = httpClient.post<FetchProvidersResponse> {
                url.encodedPath = PATH_CHECK_EMAIL
                body = FetchProvidersRequestBody(email)
            }
            if (!response.registered) throw NoSuchAccountException()
        } catch (exception: ClientRequestException) {
            val response = exception.response
            if (response.status.value == 400) {
                val text = response.readText()
                if (INVALID_IDENTIFIER in text || INVALID_EMAIL in text) {
                    throw InvalidUsernameException(exception)
                }
            }
            throw exception
        }
    }

    override suspend fun signIn(email: String, password: String): TokenPair {
        try {
            val response = httpClient.post<SignInResponse> {
                url.encodedPath = PATH_SIGN_IN
                body = SignInRequestBody(email = email, password = password)
            }
            return response.toTokenPair()
        } catch (exception: ClientRequestException) {
            val response = exception.response
            if (response.status.value == 400) {
                val text = response.readText()
                when {
                    EMAIL_NOT_FOUND in text -> throw InvalidUsernameException(exception)
                    INVALID_PASSWORD in text -> throw  InvalidPasswordException(exception)
                    USER_DISABLED in text -> throw UserDisabledException(exception)
                }
            }

            throw exception
        }
    }

    private companion object {
        private const val PATH_CHECK_EMAIL = "accounts:createAuthUri"
        private const val PATH_SIGN_IN = "accounts:signInWithPassword"

        private const val INVALID_IDENTIFIER = "INVALID_IDENTIFIER"
        private const val INVALID_EMAIL = "INVALID_EMAIL"
        private const val EMAIL_NOT_FOUND = "EMAIL_NOT_FOUND"
        private const val INVALID_PASSWORD = "INVALID_PASSWORD"
        private const val USER_DISABLED = "USER_DISABLED"
    }
}
