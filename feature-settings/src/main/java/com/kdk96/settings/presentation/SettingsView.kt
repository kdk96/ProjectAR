package com.kdk96.settings.presentation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface SettingsView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateAccountInfo(name: String, email: String, avatarUrl: String?)
    fun showImageSourceDialog()
    fun openCamera()
    fun openGallery()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress(show: Boolean)
    fun showError(resId: Int)
}