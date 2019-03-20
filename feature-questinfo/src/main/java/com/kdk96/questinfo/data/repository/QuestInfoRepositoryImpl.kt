package com.kdk96.questinfo.data.repository

import com.kdk96.common.di.Rx
import com.kdk96.questinfo.data.network.QuestInfoApi
import com.kdk96.questinfo.domain.QuestInfoRepository
import io.reactivex.Scheduler
import javax.inject.Inject

class QuestInfoRepositoryImpl @Inject constructor(
        private val api: QuestInfoApi,
        @Rx.Io private val ioScheduler: Scheduler
) : QuestInfoRepository {
    override fun getQuestInfo(questId: String) = api.getQuestInfo(questId)
            .subscribeOn(ioScheduler)

    override fun participateInQuest(questId: String) = api.participateInQuest(questId)
            .subscribeOn(ioScheduler)

    override fun cancelParticipationInQuest(playerId: String) = api.cancelParticipation(playerId)
            .subscribeOn(ioScheduler)
}