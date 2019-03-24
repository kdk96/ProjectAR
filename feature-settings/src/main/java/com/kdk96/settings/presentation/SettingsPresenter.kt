package com.kdk96.settings.presentation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.settings.R
import com.kdk96.settings.domain.AccountInteractor
import io.reactivex.Scheduler

@InjectViewState
class SettingsPresenter(
        private val router: FlowRouter,
        private val accountInteractor: AccountInteractor,
        private val mainThreadScheduler: Scheduler,
        private val screens: SettingsReachableScreens
) : BasePresenter<SettingsView>() {
    override fun onFirstViewAttach() {
        accountInteractor.accountDataChanges
                .observeOn(mainThreadScheduler)
                .subscribe { viewState.updateAccountInfo(it.name, it.email, it.photoUrl) }.connect()
        accountInteractor.getAccountData()
                .observeOn(mainThreadScheduler)
                .subscribe({}, Throwable::printStackTrace)
                .connect()
    }

    fun onAvatarClick() = viewState.showImageSourceDialog()

    fun onTakePhotoClick() = viewState.openCamera()

    fun onPickFromGalleryClick() = viewState.openGallery()

    fun onCameraOpenFailure() = viewState.showError(R.string.can_not_open_the_camera)

    fun onAvatarSelected(path: String) = accountInteractor.updateAvatar(path)
            .observeOn(mainThreadScheduler)
            .subscribe({}, Throwable::printStackTrace)
            .connect()

    fun onSignOutClick() = accountInteractor.signOut()
            .observeOn(mainThreadScheduler)
            .doOnSubscribe { viewState.showProgress(true) }
            .doOnTerminate { viewState.showProgress(false) }
            .subscribe { router.newRootFlow(screens.authFlow()) }
            .connect()

    fun onBackPressed() = router.exit()
}