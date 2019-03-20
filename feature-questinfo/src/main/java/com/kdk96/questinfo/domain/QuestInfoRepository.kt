package com.kdk96.questinfo.domain

import com.kdk96.questinfo.domain.entity.QuestInfo
import io.reactivex.Single

interface QuestInfoRepository {
    fun getQuestInfo(questId: String): Single<QuestInfo>
    fun participateInQuest(questId: String): Single<QuestInfo>
    fun cancelParticipationInQuest(playerId: String): Single<QuestInfo>
}