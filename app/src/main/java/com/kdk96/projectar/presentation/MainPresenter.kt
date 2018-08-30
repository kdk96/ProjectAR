package com.kdk96.projectar.presentation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.common.presentation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val router: Router,
        private val authInteractor: AuthInteractor
) : BasePresenter<MainView>() {
    override fun onFirstViewAttach() {
        if (authInteractor.isSignedIn()) {

        } else router.newRootScreen(Screens.SIGN_IN_SCREEN)
    }
}