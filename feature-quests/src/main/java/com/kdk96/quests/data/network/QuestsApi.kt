package com.kdk96.quests.data.network

import com.kdk96.quests.data.network.entity.QuestResponse
import io.reactivex.Single
import retrofit2.http.GET

interface QuestsApi {
    @GET("quests")
    fun getQuests(): Single<List<QuestResponse>>
}