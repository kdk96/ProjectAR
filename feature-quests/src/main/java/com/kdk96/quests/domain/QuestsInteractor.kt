package com.kdk96.quests.domain

import com.kdk96.quests.domain.entity.QuestShortInfo
import io.reactivex.Observable
import javax.inject.Inject

interface QuestsInteractor {
    fun getQuests(): Observable<List<QuestShortInfo>>
}

class QuestsInteractorImpl @Inject constructor(
        private val repository: QuestsRepository
) : QuestsInteractor {
    override fun getQuests() = repository.getQuests()
}