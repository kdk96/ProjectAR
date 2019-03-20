package com.kdk96.questinfo.domain

import com.kdk96.questinfo.domain.entity.QuestInfo
import io.reactivex.Single
import javax.inject.Inject

typealias LatLngPair = Pair<Double, Double>

interface QuestInfoInteractor {
    val questId: String
    var questInfo: QuestInfo
    fun getQuestInfo(): Single<QuestInfo>
    fun participateInQuest(): Single<QuestInfo>
    fun cancelParticipation(): Single<QuestInfo>
}

class QuestInfoInteractorImpl @Inject constructor(
        override val questId: String,
        private val questInfoRepository: QuestInfoRepository
) : QuestInfoInteractor {
    override lateinit var questInfo: QuestInfo

    override fun getQuestInfo() = questInfoRepository.getQuestInfo(questId)
            .doOnSuccess { this.questInfo = it }

    override fun participateInQuest() = questInfoRepository.participateInQuest(questId)
            .doOnSuccess { this.questInfo = it }

    override fun cancelParticipation() = questInfoRepository.cancelParticipationInQuest(questInfo.playerId!!)
            .doOnSuccess { this.questInfo = it }
}