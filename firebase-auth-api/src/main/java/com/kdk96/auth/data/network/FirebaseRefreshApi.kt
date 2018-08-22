package com.kdk96.auth.data.network

import com.kdk96.auth.data.entity.RefreshBody
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FirebaseRefreshApi {
    @POST("token")
    @FormUrlEncoded
    fun refresh(
            @Field("grant_type") grantType: String = "refresh_token",
            @Field("refresh_token") refreshToken: String
    ): Single<RefreshBody>
}