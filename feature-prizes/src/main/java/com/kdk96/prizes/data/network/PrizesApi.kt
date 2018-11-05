package com.kdk96.prizes.data.network

import com.kdk96.prizes.domain.Prize
import io.reactivex.Single
import retrofit2.http.GET

interface PrizesApi {
    @GET("account/prizes")
    fun getPrizes(): Single<List<Prize>>
}