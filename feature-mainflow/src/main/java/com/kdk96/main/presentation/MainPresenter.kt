package com.kdk96.main.presentation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.main.presentation.MainView.MenuItem
import com.kdk96.main.presentation.MainView.MenuItem.*
import com.kdk96.main.ui.MainFlowReachableScreens
import com.kdk96.settings.domain.AccountInteractor
import io.reactivex.Scheduler

@InjectViewState
class MainPresenter(
        private val accountInteractor: AccountInteractor,
        private val mainThreadScheduler: Scheduler,
        private val router: FlowRouter
) : BasePresenter<MainView>() {
    private var selectedItem: MenuItem? = null

    override fun onFirstViewAttach() {
        accountInteractor.getAccountData()
                .subscribe({}, Throwable::printStackTrace)
                .connect()
        accountInteractor.accountDataChanges
                .observeOn(mainThreadScheduler)
                .subscribe { viewState.updateAccountInfo(it.name, it.email, it.photoUrl) }
                .connect()
    }

    fun initSelectedItem(item: MenuItem) {
        selectedItem = item
    }

    fun onMenuItemSelected(item: MenuItem) {
        if (item != selectedItem) {
            when (item) {
                QUESTS -> router.newRootScreen(MainFlowReachableScreens.Quests)
                PRIZES -> router.newRootScreen(MainFlowReachableScreens.Prizes)
                SETTINGS -> router.newRootScreen(MainFlowReachableScreens.Settings)
            }
            selectedItem = item
        }
    }

    fun onExit() = router.finishFlow()
}