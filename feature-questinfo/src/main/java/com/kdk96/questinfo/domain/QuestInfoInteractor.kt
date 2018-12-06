package com.kdk96.questinfo.domain

import com.kdk96.questinfo.data.repository.QuestInfoRepository

class QuestInfoInteractor(
        private val questId: String,
        private val questInfoRepository: QuestInfoRepository
) {
    fun getQuestInfo() = questInfoRepository.getQuestInfo(questId)
}