package com.kdk96.projectar.auth.presentation.signin

import com.haroncode.gemini.binder.rule.DelegateBindingRulesFactory
import com.haroncode.gemini.binder.rule.bindEventTo
import com.haroncode.gemini.binder.rule.bindingRulesFactory
import com.kdk96.projectar.auth.ui.SignInFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInBindingRulesFactory @Inject constructor(
    private val store: SignInStore
) : DelegateBindingRulesFactory<SignInFragment>() {

    override val bindingRulesFactory = bindingRulesFactory<SignInFragment> {
        baseRule { store to it }
        rule { store bindEventTo it }
        autoCancel { store }
    }
}
