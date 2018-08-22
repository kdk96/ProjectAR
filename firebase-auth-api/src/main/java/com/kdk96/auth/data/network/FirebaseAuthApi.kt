package com.kdk96.auth.data.network

import com.kdk96.auth.data.entity.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface FirebaseAuthApi {
    @POST("createAuthUri")
    fun fetchProvidersForEmail(@Body fetchProvidersRequestBody: FetchProvidersRequestBody): Single<FetchProvidersResponseBody>

    @POST("verifyPassword")
    fun authorize(@Body authRequestBody: AuthRequestBody): Single<AuthResponseBody>

    @POST("signupNewUser")
    fun register(@Body authRequestBody: AuthRequestBody): Single<RegisterResponseBody>

    @POST("setAccountInfo")
    fun updateName(@Body nameUpdateRequestBody: NameUpdateRequestBody): Single<NameUpdateResponseBody>
}