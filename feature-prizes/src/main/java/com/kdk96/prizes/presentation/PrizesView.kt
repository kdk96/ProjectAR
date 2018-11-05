package com.kdk96.prizes.presentation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.kdk96.prizes.domain.Prize

@StateStrategyType(AddToEndSingleStrategy::class)
interface PrizesView : MvpView {
    fun showRefreshProgress(show: Boolean)
    fun showPrizes(prizes: List<Prize>)
    @StateStrategyType(SkipStrategy::class)
    fun showError(resId: Int)
}