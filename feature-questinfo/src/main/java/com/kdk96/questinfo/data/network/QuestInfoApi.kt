package com.kdk96.questinfo.data.network

import com.kdk96.questinfo.domain.entity.QuestInfo
import io.reactivex.Single
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuestInfoApi {
    @GET("quests/{questId}")
    fun getQuestInfo(@Path("questId") questId: String): Single<QuestInfo>

    @POST("quests/{questId}/participate")
    fun participateInQuest(@Path("questId") questId: String): Single<QuestInfo>

    @DELETE("players/{playerId}")
    fun cancelParticipation(@Path("playerId") playerId: String): Single<QuestInfo>
}