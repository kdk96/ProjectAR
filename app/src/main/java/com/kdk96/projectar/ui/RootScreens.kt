package com.kdk96.projectar.ui

import com.kdk96.auth.ui.AuthFlowFragment
import com.kdk96.main.ui.MainFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

interface RootScreens {
    object AuthFlow : SupportAppScreen() {
        override fun getFragment() = AuthFlowFragment()
    }

    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }
}