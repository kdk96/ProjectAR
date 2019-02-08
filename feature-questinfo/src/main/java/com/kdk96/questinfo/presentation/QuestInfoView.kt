package com.kdk96.questinfo.presentation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.kdk96.questinfo.domain.LatLngPair
import com.kdk96.questinfo.domain.entity.QuestInfo

enum class ButtonState {
    PARTICIPATE, START, WAIT
}

@StateStrategyType(AddToEndSingleStrategy::class)
interface QuestInfoView : MvpView {
    fun showInfo(questInfo: QuestInfo)
    fun showNetworkError()
    fun showStartPoint(latLngPair: LatLngPair)
    fun changeButtonState(state: ButtonState)
    fun showRemainingTime(remainingTime: Long)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showUserLocation()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCancelConfirmationDialog()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(resId: Int)
}