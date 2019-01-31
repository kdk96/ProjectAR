package com.kdk96.questinfo.domain

import com.kdk96.questinfo.data.repository.QuestInfoRepository

typealias LatLngPair = Pair<Double, Double>

class QuestInfoInteractor(
        val questId: String,
        private val questInfoRepository: QuestInfoRepository
) {
    private var playerId: String? = null

    fun initPlayerId(playerId: String?) {
        this.playerId = playerId
    }

    fun clearPlayerId() {
        playerId = null
    }

    fun getQuestInfo() = questInfoRepository.getQuestInfo(questId)
            .doOnSuccess { initPlayerId(it.playerId) }

    fun participateInQuest() = questInfoRepository.participateInQuest(questId)
            .doOnSuccess { initPlayerId(it.playerId) }

    fun cancelParticipation() = questInfoRepository.cancelParticipationInQuest(playerId!!)
            .doOnComplete { clearPlayerId() }
}