package com.kdk96.projectar.presentation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.common.presentation.Screens
import com.kdk96.projectar.presentation.MainView.MenuItem
import com.kdk96.projectar.presentation.MainView.MenuItem.*
import com.kdk96.settings.domain.AccountInteractor
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val router: Router,
        private val accountInteractor: AccountInteractor,
        @Rx.MainThread private val mainThreadScheduler: Scheduler
) : BasePresenter<MainView>() {
    private var selectedItem = MenuItem.QUESTS

    override fun onFirstViewAttach() {
        if (accountInteractor.isSignedIn()) {
            router.newRootScreen(Screens.QUESTS_SCREEN)
        } else router.newRootScreen(Screens.SIGN_IN_SCREEN)
        accountInteractor.accountDataChanges
                .observeOn(mainThreadScheduler)
                .subscribe { viewState.updateAccountInfo(it.name, it.email, it.photoUrl) }.connect()
    }

    fun onDrawerUnlocked() = accountInteractor.getAccountData()
            .observeOn(mainThreadScheduler)
            .subscribe({}, Throwable::printStackTrace)
            .connect()

    fun onMenuItemSelected(item: MenuItem) {
        if (item != selectedItem) {
            when (item) {
                QUESTS -> router.newRootScreen(Screens.QUESTS_SCREEN)
                PRIZES -> router.newRootScreen(Screens.PRIZES_SCREEN)
                SETTINGS -> router.newRootScreen(Screens.SETTINGS_SCREEN)
            }
            selectedItem = item
        }
    }
}