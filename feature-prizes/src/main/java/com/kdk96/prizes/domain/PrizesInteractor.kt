package com.kdk96.prizes.domain

import io.reactivex.Single
import javax.inject.Inject

interface PrizesInteractor {
    fun getPrizes(): Single<List<Prize>>
}

class PrizesInteractorImpl @Inject constructor(
        private val prizesRepository: PrizesRepository
) : PrizesInteractor {
    override fun getPrizes() = prizesRepository.getPrizes()
}