package com.kdk96.questinfo.presentation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.questinfo.domain.QuestInfoInteractor
import io.reactivex.Scheduler
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router

@InjectViewState
class QuestInfoPresenter(
        private val router: Router,
        private val interactor: QuestInfoInteractor,
        private val mainThreadScheduler: Scheduler
) : BasePresenter<QuestInfoView>() {
    private val startPointSubject = BehaviorSubject.create<Pair<Double, Double>>()

    override fun onFirstViewAttach() {
        interactor.getQuestInfo()
                .observeOn(mainThreadScheduler)
                .subscribe({
                    startPointSubject.onNext(it.startPointLat to it.startPointLng)
                    viewState.showInfo(it)
                }, Throwable::printStackTrace)
                .connect()
    }

    fun onMapReady() = startPointSubject.subscribe { viewState.showStartPoint(it) }.connect()

    fun onBackPressed() = router.exit()
}