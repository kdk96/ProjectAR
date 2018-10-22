package com.kdk96.quests.domain

import com.kdk96.quests.data.repository.QuestsRepository

class QuestsInteractor(
        private val repository: QuestsRepository
) {
    fun getQuests() = repository.getQuests()
}