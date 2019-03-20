package com.kdk96.prizes.domain

import io.reactivex.Single

interface PrizesRepository {
    fun getPrizes(): Single<List<Prize>>
}