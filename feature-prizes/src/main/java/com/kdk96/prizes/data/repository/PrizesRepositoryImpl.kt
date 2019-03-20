package com.kdk96.prizes.data.repository

import com.kdk96.common.di.Rx
import com.kdk96.prizes.data.network.PrizesApi
import com.kdk96.prizes.domain.PrizesRepository
import io.reactivex.Scheduler
import javax.inject.Inject

class PrizesRepositoryImpl @Inject constructor(
        private val api: PrizesApi,
        @Rx.Io private val ioScheduler: Scheduler
) : PrizesRepository {
    override fun getPrizes() = api.getPrizes().subscribeOn(ioScheduler)
}