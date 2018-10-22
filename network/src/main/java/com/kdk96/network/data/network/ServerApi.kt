package com.kdk96.network.data.network

import com.kdk96.network.data.entity.QuestResponse
import com.kdk96.network.domain.AccountData
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ServerApi {
    @GET("account/info")
    fun getAccountInfo(): Single<AccountData>

    @Multipart
    @POST("account/avatar")
    fun updateAvatar(@Part avatar: MultipartBody.Part): Single<AccountData>

    @POST("account/signout")
    fun signOut(): Completable

    @GET("quests")
    fun getQuests(): Single<List<QuestResponse>>
}