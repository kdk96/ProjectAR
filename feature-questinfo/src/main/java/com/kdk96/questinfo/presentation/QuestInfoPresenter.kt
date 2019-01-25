package com.kdk96.questinfo.presentation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.questinfo.domain.LatLngPair
import com.kdk96.questinfo.domain.QuestInfoInteractor
import com.kdk96.questinfo.domain.entity.QuestInfo
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit

@InjectViewState
class QuestInfoPresenter(
        private val router: Router,
        private val interactor: QuestInfoInteractor,
        private val mainThreadScheduler: Scheduler
) : BasePresenter<QuestInfoView>() {
    private val startPointSubject = BehaviorSubject.create<LatLngPair>()
    private var timerDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        interactor.getQuestInfo()
                .observeOn(mainThreadScheduler)
                .subscribe({
                    startPointSubject.onNext(it.startPointLat to it.startPointLng)
                    showInfo(it)
                }, Throwable::printStackTrace)
                .connect()
    }

    private fun showInfo(questInfo: QuestInfo) {
        viewState.showInfo(questInfo)
        if (questInfo.playerId == null) {
            viewState.changeButtonState(ButtonState.PARTICIPATE)
        } else {
            changeStateToStartOrWait(questInfo.startTime)
        }
    }

    private fun changeStateToStartOrWait(startTime: Long) {
        if (startTime <= System.currentTimeMillis()) {
            viewState.changeButtonState(ButtonState.START)
        } else {
            viewState.changeButtonState(ButtonState.WAIT)
            val remainingTime = startTime - System.currentTimeMillis()
            viewState.showRemainingTime(remainingTime)
            if (TimeUnit.MILLISECONDS.toDays(remainingTime) <= 1) {
                timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
                        .subscribe {
                            viewState.showRemainingTime(
                                    startTime - System.currentTimeMillis()
                            )
                        }
            }
        }
    }

    fun onMapReady() = startPointSubject.subscribe { viewState.showStartPoint(it) }.connect()

    fun onParticipateClick() = interactor.participateInQuest()
            .observeOn(mainThreadScheduler)
            .subscribe({
                changeStateToStartOrWait(it.startTime)
            }, Throwable::printStackTrace)
            .connect()

    fun onStartClick() {

    }

    fun onRemainingTimeClick() = viewState.showCancelConfirmationDialog()

    fun onCancelConfirmed() = interactor.cancelParticipation()
            .observeOn(mainThreadScheduler)
            .subscribe({
                timerDisposable?.dispose()
                viewState.changeButtonState(ButtonState.PARTICIPATE)
            }, Throwable::printStackTrace)
            .connect()

    fun onBackPressed() = router.exit()

    override fun onDestroy() {
        timerDisposable?.dispose()
        super.onDestroy()
    }
}