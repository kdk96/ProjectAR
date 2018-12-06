package com.kdk96.auth.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.textChanges
import com.kdk96.auth.di.auth.AuthComponent
import com.kdk96.auth.di.auth.DaggerAuthComponent
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

    @Inject
    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private val fieldsChangesCompositeDisposable = CompositeDisposable()
    private lateinit var authComponent: AuthComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        authComponent = getComponent {
            DaggerAuthComponent.builder()
                    .authDependencies(findComponentDependencies())
                    .build()
        }
        getComponent<SignUpComponent>().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun buildComponent() = DaggerSignUpComponent.builder()
            .email(arguments!!.getString(ARG_EMAIL)!!)
            .signUpDependencies(authComponent)
            .build()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        signUpButton.setOnClickListener {
            presenter.onSignUpClick(emailET.trimmedString, nameET.trimmedString,
                    passwordET.trimmedString, passwordConfirmationET.trimmedString)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            presenter.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
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

    override fun onDestroyView() {
        (activity as AppCompatActivity).setSupportActionBar(null)
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearComponentsOnDestroy(SignUpComponent::class.java)
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