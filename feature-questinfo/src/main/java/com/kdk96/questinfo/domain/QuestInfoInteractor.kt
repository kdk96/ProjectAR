package com.kdk96.questinfo.domain

import com.kdk96.questinfo.data.repository.QuestInfoRepository
import com.kdk96.questinfo.domain.entity.QuestInfo

typealias LatLngPair = Pair<Double, Double>

class QuestInfoInteractor(
        val questId: String,
        private val questInfoRepository: QuestInfoRepository
) {
    lateinit var questInfo: QuestInfo

    fun getQuestInfo() = questInfoRepository.getQuestInfo(questId)
            .doOnSuccess { this.questInfo = it }

    fun participateInQuest() = questInfoRepository.participateInQuest(questId)
            .doOnSuccess { this.questInfo = it }

    fun cancelParticipation() = questInfoRepository.cancelParticipationInQuest(questInfo.playerId!!)
            .doOnSuccess { this.questInfo = it }
}