package com.kdk96.quests.presenatation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.quests.R
import com.kdk96.quests.domain.QuestsInteractor
import io.reactivex.Scheduler
import java.io.IOException

@InjectViewState
class QuestsPresenter(
        private val router: FlowRouter,
        private val questsInteractor: QuestsInteractor,
        private val mainThreadScheduler: Scheduler,
        private val screens: QuestsReachableScreens
) : BasePresenter<QuestsView>() {
    override fun onFirstViewAttach() {
        viewState.showRefreshProgress(true)
        getQuests()
    }

    fun onRefresh() = getQuests()

    private fun getQuests() = questsInteractor.getQuests()
            .observeOn(mainThreadScheduler, true)
            .doAfterTerminate { viewState.showRefreshProgress(false) }
            .subscribe({
                viewState.showQuests(it)
            }, {
                when (it) {
                    is IOException -> viewState.showError(R.string.network_error)
                    else -> {
                        viewState.showError(R.string.unknown_error)
                        it.printStackTrace()
                    }
                }
            })
            .connect()

    fun onQuestClick(id: String) = router.startFlow(screens.questFlow(id))

    fun onBackPressed() = router.exit()
}