package com.kdk96.auth.ui

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.widget.textChanges
import com.kdk96.auth.di.signup.DaggerSignUpComponent
import com.kdk96.auth.di.signup.SignUpComponent
import com.kdk96.auth.presentation.signup.SignUpPresenter
import com.kdk96.auth.presentation.signup.SignUpView
import com.kdk96.auth.screen.R
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.BaseFragment
import com.kdk96.common.ui.trimmedString
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_sign_up.*
import javax.inject.Inject

class SignUpFragment : BaseFragment(), SignUpView {
    companion object {
        private const val ARG_EMAIL = "email argument"
        fun newInstance(email: String) = SignUpFragment().apply {
            arguments = Bundle().apply { putString(ARG_EMAIL, email) }
        }
    }

    override val layoutRes = R.layout.fragment_sign_up

    init {
        componentBuilder = {
            DaggerSignUpComponent.builder()
                    .email(arguments!!.getString(ARG_EMAIL)!!)
                    .childDependencies(findComponentDependencies())
                    .build()
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private val fieldsChangesCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<SignUpComponent>().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        signUpButton.setOnClickListener {
            presenter.onSignUpClick(emailET.trimmedString, nameET.trimmedString,
                    passwordET.trimmedString, passwordConfirmationET.trimmedString)
        }
    }

    override fun onResume() {
        super.onResume()
        emailET.textChanges().subscribe { presenter.onEmailChanged(it.toString()) }.connect()
        nameET.textChanges().subscribe { presenter.onNameChanged(it.toString()) }.connect()
        passwordET.textChanges().subscribe { presenter.onPasswordChanged(it.toString()) }.connect()
        passwordConfirmationET.textChanges().subscribe { presenter.onPasswordConfirmationChanged(it.toString()) }.connect()
    }

    override fun onPause() {
        fieldsChangesCompositeDisposable.clear()
        super.onPause()
    }

    override fun setEmail(email: String) = emailET.setText(email)

    override fun showProgress(show: Boolean) = showProgressDialog(show, R.string.registration)

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

    override fun showNameError(resId: Int) {
        nameTIL.error = getString(resId)
    }

    override fun hideNameError() {
        nameTIL.isErrorEnabled = false
    }

    override fun showPasswordConfirmationError(resId: Int) {
        passwordConfirmationTIL.error = getString(resId)
    }

    override fun hidePasswordConfirmationError() {
        passwordConfirmationTIL.isErrorEnabled = false
    }

    override fun showError(resId: Int) {
        Snackbar.make(view!!, resId, Snackbar.LENGTH_SHORT).show()
    }

    private fun Disposable.connect() {
        fieldsChangesCompositeDisposable.add(this)
    }

    override fun onBackPressed() = presenter.onBackPressed()
}