package com.kdk96.prizes.presentation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.prizes.R
import com.kdk96.prizes.domain.PrizesInteractor
import io.reactivex.Scheduler
import java.io.IOException

@InjectViewState
class PrizesPresenter(
        private val router: FlowRouter,
        private val interactor: PrizesInteractor,
        private val mainThreadScheduler: Scheduler
) : BasePresenter<PrizesView>() {
    override fun onFirstViewAttach() {
        viewState.showRefreshProgress(true)
        getPrizes()
    }

    fun onRefresh() = getPrizes()

    private fun getPrizes() = interactor.getPrizes()
            .observeOn(mainThreadScheduler)
            .doAfterTerminate { viewState.showRefreshProgress(false) }
            .subscribe({
                viewState.showPrizes(it)
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

    fun onBackPressed() = router.exit()
}