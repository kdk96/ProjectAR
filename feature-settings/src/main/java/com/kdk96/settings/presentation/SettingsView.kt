package com.kdk96.settings.presentation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SettingsView : MvpView {
    fun updateAccountInfo(name: String, email: String, avatarUrl: String?)
    fun showImageSourceDialog(show: Boolean)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openCamera()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openGallery()
    fun showProgress(show: Boolean)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(resId: Int)
}