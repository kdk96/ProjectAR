package com.kdk96.prizes.data.repository

import com.kdk96.prizes.data.network.PrizesApi
import io.reactivex.Scheduler

class PrizesRepository(
        private val api: PrizesApi,
        private val ioScheduler: Scheduler
) {
    fun getPrizes() = api.getPrizes().subscribeOn(ioScheduler)
}