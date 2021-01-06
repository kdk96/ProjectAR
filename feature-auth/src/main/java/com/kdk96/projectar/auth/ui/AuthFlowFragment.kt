package com.kdk96.projectar.auth.ui

import android.os.Bundle
import com.github.terrakok.cicerone.Replace
import com.kdk96.projectar.common.ui.FlowFragment
import com.kdk96.tanto.android.inject

class AuthFlowFragment : FlowFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf(Replace(AuthFlowScreens.SignIn)))
        }
    }
}
