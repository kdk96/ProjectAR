package com.kdk96.auth.presentation.signup

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface SignUpView : MvpView {
    fun setEmail(email: String)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress(show: Boolean)
    fun showEmailError(resId: Int)
    fun hideEmailError()
    fun showPasswordError(resId: Int)
    fun hidePasswordError()
    fun showNameError(resId: Int)
    fun hideNameError()
    fun showPasswordConfirmationError(resId: Int)
    fun hidePasswordConfirmationError()
    @StateStrategyType(SkipStrategy::class)
    fun showError(resId: Int)
}