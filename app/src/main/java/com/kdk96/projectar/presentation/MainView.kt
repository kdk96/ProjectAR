package com.kdk96.projectar.presentation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    enum class MenuItem {
        QUESTS,
        PRIZES,
        SETTINGS
    }

    fun updateAccountInfo(name: String, email: String, avatarUrl: String?)
}