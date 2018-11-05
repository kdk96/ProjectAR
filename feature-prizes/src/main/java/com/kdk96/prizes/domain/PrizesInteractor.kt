package com.kdk96.prizes.domain

import com.kdk96.prizes.data.repository.PrizesRepository

class PrizesInteractor(private val prizesRepository: PrizesRepository) {
    fun getPrizes() = prizesRepository.getPrizes()
}