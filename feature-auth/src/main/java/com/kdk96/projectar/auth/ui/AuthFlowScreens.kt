package com.kdk96.projectar.auth.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen

object AuthFlowScreens {
    object SignIn : FragmentScreen(fragmentCreator = { SignInFragment() })
}