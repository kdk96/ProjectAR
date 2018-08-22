package com.kdk96.auth.data.network

import com.kdk96.auth.data.entity.*
import com.kdk96.auth.data.repository.AuthApi
import com.kdk96.auth.domain.AccountCollisionException
import com.kdk96.auth.domain.FieldName
import com.kdk96.auth.domain.InvalidFieldException
import com.kdk96.auth.domain.NoSuchAccountException
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.HttpException

class FirebaseAuthImpl constructor(
        private val firebaseAuthApi: FirebaseAuthApi,
        private val firebaseRefreshApi: FirebaseRefreshApi
) : AuthApi {
    override fun checkEmail(email: String) =
            firebaseAuthApi.fetchProvidersForEmail(FetchProvidersRequestBody(email))
                    .doOnSuccess { if (!it.registered) throw NoSuchAccountException() }
                    .onErrorResumeNext { Single.error(mapError(it)) }
                    .ignoreElement()

    override fun authorize(email: String, password: String) =
            firebaseAuthApi.authorize(AuthRequestBody(email, password))
                    .onErrorResumeNext { Single.error(mapError(it)) }
                    .map { AuthData(it.idToken, it.refreshToken, it.email, it.displayName) }

    override fun register(email: String, password: String, name: String) =
            firebaseAuthApi.register(AuthRequestBody(email, password))
                    .flatMap {
                        firebaseAuthApi.updateName(NameUpdateRequestBody(it.idToken, name))
                                .onErrorReturn { _ -> NameUpdateResponseBody("") }
                                .map { nameUpdateBody ->
                                    AuthData(it.idToken, it.refreshToken,
                                            it.email, nameUpdateBody.displayName)
                                }
                    }.onErrorResumeNext { Single.error(mapError(it)) }

    private fun mapError(throwable: Throwable): Throwable {
        var exception = throwable
        if (throwable is HttpException && throwable.code() == 400) {
            throwable.response().errorBody()?.string()?.let {
                val message = JSONObject(it).optJSONObject("error")?.optString("message")
                if (message == "INVALID_IDENTIFIER" || message == "INVALID_EMAIL") exception = InvalidFieldException(setOf(FieldName.EMAIL))
                if (message == "EMAIL_NOT_FOUND") exception = NoSuchAccountException()
                if (message == "INVALID_PASSWORD" || message == "WEAK_PASSWORD") exception = InvalidFieldException(setOf(FieldName.PASSWORD))
                if (message == "EMAIL_EXISTS") exception = AccountCollisionException()
            }
        }
        return exception
    }

    override fun refresh(refreshToken: String) =
            firebaseRefreshApi.refresh(refreshToken = refreshToken)
                    .map { TokenPair(it.idToken, it.refreshToken) }
}