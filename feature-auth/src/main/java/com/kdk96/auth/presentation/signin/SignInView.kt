package com.kdk96.auth.presentation.signin

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface SignInView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress(show: Boolean)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPasswordField(show: Boolean)
    fun showEmailError(resId: Int)
    fun hideEmailError()
    fun showPasswordError(resId: Int)
    fun hidePasswordError()
    @StateStrategyType(SkipStrategy::class)
    fun showError(resId: Int)
}