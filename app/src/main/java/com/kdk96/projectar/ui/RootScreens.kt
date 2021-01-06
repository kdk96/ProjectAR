package com.kdk96.projectar.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.kdk96.projectar.auth.ui.AuthFlowFragment

//import com.kdk96.projectar.auth.ui.AuthFlowFragment
//import com.kdk96.main.ui.MainFlowFragment
//import ru.terrakok.cicerone.android.support.SupportAppScreen
//
object RootScreens {
    object AuthFlow : FragmentScreen(fragmentCreator = { AuthFlowFragment() })
//
//    object MainFlow : SupportAppScreen() {
//        override fun getFragment() = MainFlowFragment()
//    }
}