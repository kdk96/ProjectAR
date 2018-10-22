package com.kdk96.quests.presenatation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.kdk96.quests.domain.Quest

@StateStrategyType(AddToEndSingleStrategy::class)
interface QuestsView : MvpView {
    fun showRefreshProgress(show: Boolean)
    fun showQuests(quests: List<Quest>)
}