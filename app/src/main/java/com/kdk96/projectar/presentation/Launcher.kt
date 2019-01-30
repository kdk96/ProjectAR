package com.kdk96.projectar.presentation

import com.kdk96.projectar.ui.RootScreens
import com.kdk96.settings.domain.AccountInteractor
import ru.terrakok.cicerone.Router

class Launcher(
        private val router: Router,
        private val accountInteractor: AccountInteractor
) {
    fun coldStart() {
        val rootScreen =
                if (accountInteractor.isSignedIn()) RootScreens.MainFlow
                else RootScreens.AuthFlow
        router.newRootScreen(rootScreen)
    }
}