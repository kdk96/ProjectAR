package com.kdk96.auth.ui

import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintSet
import android.support.design.widget.Snackbar
import android.support.transition.TransitionManager
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.textChanges
import com.kdk96.auth.di.signin.DaggerSignInComponent
import com.kdk96.auth.di.signin.SignInComponent
import com.kdk96.auth.presentation.signin.SignInPresenter
import com.kdk96.auth.presentation.signin.SignInView
import com.kdk96.auth.screen.R
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.BaseFragment
import com.kdk96.common.ui.hideKeyboard
import com.kdk96.common.ui.trimmedString
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.inject.Inject

class SignInFragment : BaseFragment(), SignInView {
    override val layoutRes = R.layout.fragment_sign_in

    init {
        componentBuilder = {
            DaggerSignInComponent.builder().childDependencies(findComponentDependencies()).build()
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: SignInPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private val animationHandler = Handler()
    private val fieldsChangesCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<SignInComponent>().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signInButton.setOnClickListener {
            hideKeyboardAndStart { presenter.onSignInClick(emailET.trimmedString) }
        }
        signUpButton.setOnClickListener { presenter.onSignUpClick() }
        emailET.setOnEditorActionListener(OnActionDone {
            presenter.onSignInClick(emailET.trimmedString)
        })
        passwordET.setOnEditorActionListener(OnActionDone {
            presenter.onSignInClick(emailET.trimmedString, passwordET.trimmedString)
        })
    }

    inner class OnActionDone(private val action: () -> Unit) : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?) =
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hideKeyboardAndStart(action)
                    true
                } else false
    }

    override fun onResume() {
        super.onResume()
        emailET.textChanges().subscribe { presenter.onEmailChanged(it.toString()) }.connect()
        passwordET.textChanges().subscribe { presenter.onPasswordChanged(it.toString()) }.connect()
    }

    override fun onPause() {
        fieldsChangesCompositeDisposable.clear()
        super.onPause()
    }

    override fun showProgress(show: Boolean) = ConstraintSet().run {
        clone(constraintLayout)
        val (progressBarVisibility, buttonVisibility) =
                if (show) ConstraintSet.VISIBLE to ConstraintSet.GONE
                else ConstraintSet.GONE to ConstraintSet.VISIBLE
        setVisibility(R.id.progressBar, progressBarVisibility)
        setVisibility(R.id.signInButton, buttonVisibility)
        setVisibility(R.id.signUpButton, buttonVisibility)
        TransitionManager.beginDelayedTransition(constraintLayout)
        applyTo(constraintLayout)
    }

    override fun showPasswordField(show: Boolean) = ConstraintSet().run {
        clone(constraintLayout)
        val (visibility, onSignInClick) = if (show)
            ConstraintSet.VISIBLE to
                    { presenter.onSignInClick(emailET.trimmedString, passwordET.trimmedString) }
        else {
            passwordET.text = null
            passwordTIL.isErrorEnabled = false
            ConstraintSet.GONE to { presenter.onSignInClick(emailET.trimmedString) }
        }
        setVisibility(R.id.passwordTIL, visibility)
        TransitionManager.beginDelayedTransition(constraintLayout)
        applyTo(constraintLayout)
        signInButton.setOnClickListener { hideKeyboardAndStart(onSignInClick) }
    }

    override fun showEmailError(resId: Int) {
        emailTIL.error = getString(resId)
    }

    override fun hideEmailError() {
        emailTIL.isErrorEnabled = false
    }

    override fun showPasswordError(resId: Int) {
        passwordTIL.error = getString(resId)
    }

    override fun hidePasswordError() {
        passwordTIL.isErrorEnabled = false
    }

    override fun showError(resId: Int) {
        Snackbar.make(constraintLayout, resId, Snackbar.LENGTH_LONG).show()
    }

    private fun hideKeyboardAndStart(action: (() -> Unit)) {
        hideKeyboard()
        animationHandler.postDelayed(action, 25)
    }

    private fun Disposable.connect() {
        fieldsChangesCompositeDisposable.add(this)
    }

    override fun onBackPressed() = presenter.onBackPressed()
}