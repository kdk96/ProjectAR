package com.kdk96.questinfo.presentation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.questinfo.R
import com.kdk96.questinfo.domain.LatLngPair
import com.kdk96.questinfo.domain.QuestInfoInteractor
import com.kdk96.questinfo.domain.entity.QuestInfo
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router
import java.io.IOException
import java.util.concurrent.TimeUnit

@InjectViewState
class QuestInfoPresenter(
        private val router: Router,
        private val interactor: QuestInfoInteractor,
        private val mainThreadScheduler: Scheduler,
        private val screens: QuestInfoReachableScreens
) : BasePresenter<QuestInfoView>() {
    private val startPointSubject = BehaviorSubject.create<LatLngPair>()
    private var startPointDisposable: Disposable? = null
    private val questInfoSubject = BehaviorSubject.create<QuestInfo>()
    private var questInfoDisposable: Disposable? = null
    private var timerDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        viewState.showUserLocation()
        getQuestInfo()
    }

    fun onRetryClick() = getQuestInfo()

    private fun getQuestInfo() = interactor.getQuestInfo()
            .observeOn(mainThreadScheduler)
            .subscribe({
                startPointSubject.onNext(it.startPointLat to it.startPointLng)
                questInfoSubject.onNext(it)
            }, {
                when (it) {
                    is IOException -> viewState.showNetworkError()
                    else -> {
                        viewState.showError(R.string.unknown_error)
                        it.printStackTrace()
                    }
                }
            }).connect()

    fun onResume() {
        questInfoDisposable = questInfoSubject.subscribe(::showInfo)
    }

    fun onPause() {
        timerDisposable?.dispose()
        questInfoDisposable?.dispose()
    }

    private fun showInfo(questInfo: QuestInfo) {
        viewState.showInfo(questInfo)
        if (questInfo.playerId == null) changeStateToParticipate()
        else changeStateToStartOrWait(questInfo.startTime)
    }

    private fun changeStateToParticipate() {
        timerDisposable?.dispose()
        viewState.changeButtonState(ButtonState.PARTICIPATE)
    }

    private fun changeStateToStartOrWait(startTime: Long) {
        val remainingTime = startTime - System.currentTimeMillis()
        if (remainingTime <= 0) {
            viewState.changeButtonState(ButtonState.START)
        } else {
            viewState.changeButtonState(ButtonState.WAIT)
            viewState.showRemainingTime(remainingTime)
            if (TimeUnit.MILLISECONDS.toDays(remainingTime) <= 1) {
                timerDisposable = Completable.timer(1, TimeUnit.SECONDS)
                        .subscribe { changeStateToStartOrWait(startTime) }
            }
        }
    }

    fun onMapReady() {
        startPointDisposable = startPointSubject.subscribe { viewState.showStartPoint(it) }
    }

    fun onMapDestroy() {
        startPointDisposable?.dispose()
    }

    fun onParticipateClick() = interactor.participateInQuest()
            .observeOn(mainThreadScheduler)
            .subscribe(questInfoSubject::onNext, ::onError)
            .connect()

    fun onStartClick() = router.replaceScreen(screens.questFlow(interactor.questId))

    fun onRemainingTimeClick() = viewState.showCancelConfirmationDialog()

    fun onCancelConfirmed() = interactor.cancelParticipation()
            .observeOn(mainThreadScheduler)
            .subscribe(questInfoSubject::onNext, ::onError)
            .connect()

    private fun onError(throwable: Throwable) = when (throwable) {
        is IOException -> viewState.showError(R.string.network_error)
        else -> {
            viewState.showError(R.string.unknown_error)
            throwable.printStackTrace()
        }
    }

    fun onBackPressed() = router.exit()
}