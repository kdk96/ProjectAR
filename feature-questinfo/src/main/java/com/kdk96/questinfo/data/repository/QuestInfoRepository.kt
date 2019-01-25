package com.kdk96.questinfo.data.repository

import com.kdk96.questinfo.data.network.QuestInfoApi
import io.reactivex.Scheduler

class QuestInfoRepository(
        private val api: QuestInfoApi,
        private val ioScheduler: Scheduler
) {
    fun getQuestInfo(questId: String) = api.getQuestInfo(questId)
            .subscribeOn(ioScheduler)

    fun participateInQuest(questId: String) = api.participateInQuest(questId)
            .subscribeOn(ioScheduler)

    fun cancelParticipationInQuest(playerId: String) = api.cancelParticipation(playerId)
            .subscribeOn(ioScheduler)
}