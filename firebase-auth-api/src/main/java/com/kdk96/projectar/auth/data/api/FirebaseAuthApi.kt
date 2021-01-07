package com.kdk96.projectar.auth.data.api

import com.kdk96.projectar.auth.data.entity.FetchProvidersRequestBody
import com.kdk96.projectar.auth.data.entity.FetchProvidersResponse
import com.kdk96.projectar.auth.domain.InvalidUsernameException
import com.kdk96.projectar.auth.domain.NoSuchAccountException
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
//        httpClient.post()
//
//            firebaseAuthApi.fetchProvidersForEmail(FetchProvidersRequestBody(email))
//                    .doOnSuccess { if (!it.registered) throw NoSuchAccountException() }
//                    .onErrorResumeNext { Single.error(mapError(it)) }
//                    .ignoreElement()

//    override fun authorize(email: String, password: String) =
//        firebaseAuthApi.authorize(AuthRequestBody(email, password))
//            .onErrorResumeNext { Single.error(mapError(it)) }
//            .map { AuthData(it.idToken, it.refreshToken, it.email, it.displayName) }
//
//    override fun register(email: String, password: String, name: String) =
//        firebaseAuthApi.register(AuthRequestBody(email, password))
//            .flatMap {
//                firebaseAuthApi.updateName(NameUpdateRequestBody(it.idToken, name))
//                    .onErrorReturn { _ -> NameUpdateResponseBody("") }
//                    .map { nameUpdateBody ->
//                        AuthData(
//                            it.idToken, it.refreshToken,
//                            it.email, nameUpdateBody.displayName
//                        )
//                    }
//            }.onErrorResumeNext { Single.error(mapError(it)) }
//
//    private fun mapError(throwable: Throwable): Throwable {
//        var exception = throwable
//        if (throwable is HttpException && throwable.code() == 400) {
//            throwable.response().errorBody()?.string()?.let {
//                val message = JSONObject(it).optJSONObject("error")?.optString("message")
//                if (message == "INVALID_IDENTIFIER" || message == "INVALID_EMAIL") exception =
//                    InvalidFieldException(setOf(FieldName.EMAIL))
//                if (message == "EMAIL_NOT_FOUND") exception = NoSuchAccountException()
//                if (message == "INVALID_PASSWORD" || message == "WEAK_PASSWORD") exception =
//                    InvalidFieldException(setOf(FieldName.PASSWORD))
//                if (message == "EMAIL_EXISTS") exception = AccountCollisionException()
//            }
//        }
//        return exception
//    }
//
//    override fun refresh(refreshToken: String) =
//        firebaseRefreshApi.refresh(refreshToken = refreshToken)
//            .map { TokenPair(it.idToken, it.refreshToken) }

    private companion object {
        private const val PATH_CHECK_EMAIL = "accounts:createAuthUri"

        private const val INVALID_IDENTIFIER = "INVALID_IDENTIFIER"
        private const val INVALID_EMAIL = "INVALID_EMAIL"
    }
}
