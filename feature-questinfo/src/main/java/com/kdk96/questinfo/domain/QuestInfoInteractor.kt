package com.kdk96.questinfo.domain

import com.kdk96.questinfo.data.repository.QuestInfoRepository

typealias LatLngPair = Pair<Double, Double>

class QuestInfoInteractor(
        private val questId: String,
        private val questInfoRepository: QuestInfoRepository
) {
    var playerId: String? = null

    fun getQuestInfo() = questInfoRepository.getQuestInfo(questId)
            .doOnSuccess { playerId = it.playerId }

    fun participateInQuest() = questInfoRepository.participateInQuest(questId)
            .doOnSuccess { playerId = it.playerId }

    fun cancelParticipation() = questInfoRepository.cancelParticipationInQuest(playerId!!)
            .doOnComplete { playerId = null }
}