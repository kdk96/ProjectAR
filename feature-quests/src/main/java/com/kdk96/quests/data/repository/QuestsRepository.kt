package com.kdk96.quests.data.repository

import com.kdk96.network.data.network.ServerApi
import com.kdk96.quests.domain.Quest
import io.reactivex.Observable
import io.reactivex.Scheduler

class QuestsRepository(
        private val api: ServerApi,
        private val ioScheduler: Scheduler
) {
    fun getQuests() = api.getQuests()
            .flatMapObservable { Observable.fromIterable(it) }
            .map {
                Quest(
                        it.id,
                        it.title,
                        it.startTime,
                        it.companyName,
                        it.companyLogoUrl,
                        it.grandPrizeDescription
                )
            }
            .toList()
            .subscribeOn(ioScheduler)
}