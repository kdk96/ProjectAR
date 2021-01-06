package com.kdk96.projectar.auth.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.haroncode.gemini.binder.StoreViewBinding
import com.kdk96.projectar.auth.presentation.signin.SignInBindingRulesFactory
import com.kdk96.projectar.auth.presentation.signin.SignInStore
import com.kdk96.projectar.auth.screen.R
import com.kdk96.projectar.auth.screen.databinding.FragmentSignInBinding
import com.kdk96.projectar.common.domain.validation.Valid
import com.kdk96.projectar.common.domain.validation.Violation
import com.kdk96.projectar.common.ui.BaseFragment
import com.kdk96.tanto.android.inject
import javax.inject.Inject

class SignInFragment : BaseFragment<SignInStore.Action, SignInStore.State>(R.layout.fragment_sign_in) {

    @Inject
    lateinit var bindingRulesFactory: SignInBindingRulesFactory

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        StoreViewBinding.with { bindingRulesFactory }
            .bind(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignInBinding.bind(view)
        binding.apply {
            emailET.addTextChangedListener { postAction(SignInStore.Action.ChangeUsername(it?.toString().orEmpty())) }
            signInButton.setOnClickListener { postAction(SignInStore.Action.SignIn) }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onStateChanged(state: SignInStore.State) {
        binding.emailTIL.isEnabled = !state.isLoading
        binding.passwordTIL.isEnabled = !state.isLoading
        binding.signInButton.isEnabled = !state.isLoading
        binding.signUpButton.isEnabled = !state.isLoading
        binding.progressBar.isVisible = state.isLoading

        binding.emailTIL.error = (state.email.verifiable as? Violation)?.message
        binding.passwordTIL.isVisible = state.email.verifiable is Valid
    }

//
//
//    @Inject
//    @InjectPresenter
//    lateinit var presenter: SignInPresenter
//
//    @ProvidePresenter
//    fun providePresenter() = presenter
//
//    @Inject
//    lateinit var signInFactory:SignInBindingRulesFactory
//
//    private val animationHandler = Handler()
//    private val fieldsChangesCompositeDisposable = CompositeDisposable()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
////        getComponent<SignInComponent>().inject(this)
//        super.onCreate(savedInstanceState)
//
//        StoreViewBinding
//            .with { signInFactory }
//            .bind(this)
//    }
//
//    private val actionChannel = Channel<SignInStore.Action>()
//
//    override val actionFlow: Flow<SignInStore.Action>
//        get() = actionChannel.receiveAsFlow()
//
//    override fun accept(value: SignInStore.State) {
//
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        signInButton.setOnClickListener {
//            hideKeyboardAndStart { presenter.onSignInClick(emailET.trimmedString) }
//
//            lifecycleScope.launch {
//                actionChannel.send(SignInStore.Action.SignIn)
//            }
//        }
//        signUpButton.setOnClickListener { presenter.onSignUpClick() }
//        emailET.setOnEditorActionListener(OnActionDone {
//            presenter.onSignInClick(emailET.trimmedString)
//        })
//        passwordET.setOnEditorActionListener(OnActionDone {
//            presenter.onSignInClick(emailET.trimmedString, passwordET.trimmedString)
//        })
//    }
//
//    inner class OnActionDone(private val action: () -> Unit) : TextView.OnEditorActionListener {
//        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?) =
//                if (actionId == EditorInfo.IME_ACTION_DONE ||
//                        (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    hideKeyboardAndStart(action)
//                    true
//                } else false
//    }
//
//    override fun onResume() {
//        super.onResume()
//        emailET.textChanges().subscribe { presenter.onEmailChanged(it.toString()) }.connect()
//        passwordET.textChanges().subscribe { presenter.onPasswordChanged(it.toString()) }.connect()
//    }
//
//    override fun onPause() {
//        fieldsChangesCompositeDisposable.clear()
//        super.onPause()
//    }
//
//    override fun showProgress(show: Boolean) = ConstraintSet().run {
//        clone(constraintLayout)
//        val (progressBarVisibility, buttonVisibility) =
//                if (show) ConstraintSet.VISIBLE to ConstraintSet.GONE
//                else ConstraintSet.GONE to ConstraintSet.VISIBLE
//        setVisibility(R.id.progressBar, progressBarVisibility)
//        setVisibility(R.id.signInButton, buttonVisibility)
//        setVisibility(R.id.signUpButton, buttonVisibility)
//        TransitionManager.beginDelayedTransition(constraintLayout)
//        applyTo(constraintLayout)
//    }
//
//    override fun showPasswordField(show: Boolean) = ConstraintSet().run {
//        clone(constraintLayout)
//        val (visibility, onSignInClick) = if (show)
//            ConstraintSet.VISIBLE to
//                    { presenter.onSignInClick(emailET.trimmedString, passwordET.trimmedString) }
//        else {
//            passwordET.text = null
//            passwordTIL.isErrorEnabled = false
//            ConstraintSet.GONE to { presenter.onSignInClick(emailET.trimmedString) }
//        }
//        setVisibility(R.id.passwordTIL, visibility)
//        TransitionManager.beginDelayedTransition(constraintLayout)
//        applyTo(constraintLayout)
//        signInButton.setOnClickListener { hideKeyboardAndStart(onSignInClick) }
//    }
//
//    override fun showEmailError(resId: Int) {
//        emailTIL.error = getString(resId)
//    }
//
//    override fun hideEmailError() {
//        emailTIL.isErrorEnabled = false
//    }
//
//    override fun showPasswordError(resId: Int) {
//        passwordTIL.error = getString(resId)
//    }
//
//    override fun hidePasswordError() {
//        passwordTIL.isErrorEnabled = false
//    }
//
//    override fun showError(resId: Int) {
//        Snackbar.make(constraintLayout, resId, Snackbar.LENGTH_LONG).show()
//    }
//
//    private fun hideKeyboardAndStart(action: (() -> Unit)) {
//        hideKeyboard()
//        animationHandler.postDelayed(action, 25)
//    }
//
//    private fun Disposable.connect() {
//        fieldsChangesCompositeDisposable.add(this)
//    }
//
////    override fun onBackPressed() = presenter.onBackPressed()
}
