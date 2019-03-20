package com.kdk96.quests.domain

import com.kdk96.quests.domain.entity.QuestShortInfo
import io.reactivex.Observable

interface QuestsRepository {
    fun getQuests(): Observable<List<QuestShortInfo>>
}