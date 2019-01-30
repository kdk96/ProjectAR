package com.kdk96.auth.presentation.signup

import com.arellomobile.mvp.InjectViewState
import com.kdk96.auth.domain.*
import com.kdk96.auth.screen.R
import com.kdk96.auth.ui.AuthFlowReachableScreens
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.common.presentation.FlowRouter
import io.reactivex.Scheduler
import java.io.IOException
import javax.inject.Inject

@InjectViewState
class SignUpPresenter @Inject constructor(
        private var email: String,
        private val interactor: AuthInteractor,
        private val router: FlowRouter,
        @Rx.MainThread private val mainThreadScheduler: Scheduler,
        private val screens: AuthFlowReachableScreens
) : BasePresenter<SignUpView>() {
    private var name = ""
    private var password = ""
    private var passwordConfirmation = ""

    override fun onFirstViewAttach() {
        viewState.setEmail(email)
    }

    fun onEmailChanged(email: String) {
        if (this.email == email) return
        this.email = email
        viewState.hideEmailError()
    }

    fun onNameChanged(name: String) {
        if (this.name == name) return
        this.name = name
        viewState.hideNameError()
    }

    fun onPasswordChanged(password: String) {
        if (this.password == password) return
        this.password = password
        viewState.hidePasswordError()
    }

    fun onPasswordConfirmationChanged(passwordConfirmation: String) {
        if (this.passwordConfirmation == passwordConfirmation) return
        this.passwordConfirmation = passwordConfirmation
        viewState.hidePasswordConfirmationError()
    }

    fun onSignUpClick(email: String, name: String, password: String, passwordConfirmation: String) =
            interactor.signUp(email, name, password, passwordConfirmation)
                    .observeOn(mainThreadScheduler)
                    .doOnSubscribe { viewState.showProgress(true) }
                    .subscribe({ router.newRootFlow(screens.mainFlow()) },
                            {
                                viewState.showProgress(false)
                                onError(it)
                            })
                    .connect()

    private fun onError(throwable: Throwable) {
        when (throwable) {
            is IOException -> viewState.showError(R.string.network_error)
            is EmptyFieldException -> with(throwable.fields) {
                if (contains(FieldName.EMAIL))
                    viewState.showEmailError(R.string.empty_email_error)
                if (contains(FieldName.PASSWORD))
                    viewState.showPasswordError(R.string.empty_password_error)
                if (contains(FieldName.NAME))
                    viewState.showNameError(R.string.empty_name_error)
                if (contains(FieldName.PASSWORD_CONFIRMATION))
                    viewState.showPasswordConfirmationError(R.string.empty_password_confirmation_error)
            }
            is InvalidFieldException -> with(throwable.fields) {
                if (contains(FieldName.EMAIL))
                    viewState.showEmailError(R.string.invalid_email_error)
                if (contains(FieldName.PASSWORD))
                    viewState.showPasswordError(R.string.invalid_new_password_error)
                if (contains(FieldName.PASSWORD_CONFIRMATION))
                    viewState.showPasswordConfirmationError(R.string.passwords_do_not_match_error)
            }
            is AccountCollisionException -> viewState.showEmailError(R.string.account_collision_error)
            else -> viewState.showError(R.string.unknown_error)
        }
    }

    fun onBackPressed() = router.exit()
}