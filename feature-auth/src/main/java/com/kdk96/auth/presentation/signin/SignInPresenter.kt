package com.kdk96.auth.presentation.signin

import com.arellomobile.mvp.InjectViewState
import com.kdk96.auth.domain.*
import com.kdk96.auth.screen.R
import com.kdk96.auth.ui.AuthFlowReachableScreens
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.common.presentation.FlowRouter
import io.reactivex.Scheduler
import java.io.IOException

@InjectViewState
class SignInPresenter(
        private val interactor: AuthInteractor,
        private val router: FlowRouter,
        private val mainThreadScheduler: Scheduler,
        private val screens: AuthFlowReachableScreens
) : BasePresenter<SignInView>() {
    private var email = ""
    private var password = ""

    fun onEmailChanged(email: String) {
        if (this.email == email) return
        this.email = email
        viewState.hideEmailError()
        viewState.showPasswordField(false)
    }

    fun onPasswordChanged(password: String) {
        if (this.password == password) return
        this.password = password
        viewState.hidePasswordError()
    }

    fun onSignInClick(email: String) = interactor.checkEmail(email)
            .observeOn(mainThreadScheduler)
            .doOnSubscribe { viewState.showProgress(true) }
            .doOnTerminate { viewState.showProgress(false) }
            .subscribe({ viewState.showPasswordField(true) },
                    ::onError)
            .connect()

    fun onSignInClick(email: String, password: String) = interactor.signIn(email, password)
            .observeOn(mainThreadScheduler)
            .doOnSubscribe { viewState.showProgress(true) }
            .subscribe(
                    { router.newRootFlow(screens.mainFlow()) },
                    {
                        viewState.showProgress(false)
                        onError(it)
                    }
            ).connect()

    private fun onError(throwable: Throwable) {
        when (throwable) {
            is EmptyFieldException -> with(throwable) {
                if (fields.contains(FieldName.EMAIL))
                    viewState.showEmailError(R.string.empty_email_error)
                if (fields.contains(FieldName.PASSWORD))
                    viewState.showPasswordError(R.string.empty_password_error)
            }
            is IOException -> viewState.showError(R.string.network_error)
            is InvalidFieldException -> with(throwable) {
                if (fields.contains(FieldName.EMAIL))
                    viewState.showEmailError(R.string.invalid_email_error)
                if (fields.contains(FieldName.PASSWORD))
                    viewState.showPasswordError(R.string.invalid_password_error)
            }
            is NoSuchAccountException -> viewState.showEmailError(R.string.no_such_account_error)
            else -> viewState.showError(R.string.unknown_error)
        }
    }

    fun onSignUpClick() = router.navigateTo(AuthFlowReachableScreens.SignUp(email))

    fun onBackPressed() = router.exit()
}