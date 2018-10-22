package com.kdk96.quests.presenatation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.quests.domain.QuestsInteractor
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router

@InjectViewState
class QuestsPresenter(
        private val router: Router,
        private val interactor: QuestsInteractor,
        private val mainThreadScheduler: Scheduler
) : BasePresenter<QuestsView>() {
    override fun onFirstViewAttach() {
        viewState.showRefreshProgress(true)
        getQuests()
    }

    fun onRefresh() {
        getQuests()
    }

    private fun getQuests() = interactor.getQuests()
            .observeOn(mainThreadScheduler)
            .doAfterTerminate { viewState.showRefreshProgress(false) }
            .subscribe({
                viewState.showQuests(it)
            }, Throwable::printStackTrace)
            .connect()

    fun onBackPressed() = router.exit()
}