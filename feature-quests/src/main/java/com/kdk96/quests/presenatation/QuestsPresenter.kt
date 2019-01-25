package com.kdk96.quests.presenatation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.common.presentation.Screens
import com.kdk96.quests.R
import com.kdk96.quests.domain.QuestsInteractor
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router
import java.io.IOException

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

    fun onRefresh() = getQuests()

    private fun getQuests() = interactor.getQuests()
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

    fun onQuestClick(id: String) = router.navigateTo(Screens.QUEST_INFO_SCREEN, id)

    fun onBackPressed() = router.exit()
}