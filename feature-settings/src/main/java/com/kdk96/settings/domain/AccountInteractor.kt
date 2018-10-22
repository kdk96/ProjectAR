package com.kdk96.settings.domain

import com.kdk96.common.presentation.Screens
import com.kdk96.network.domain.AccountData
import com.kdk96.settings.data.repository.AccountRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router

class AccountInteractor(
        private val accountRepository: AccountRepository,
        private val router: Router,
        private val mainScheduler: Scheduler
) {
    init {
        accountRepository.subscribeToRefreshFailure {
            signOut().observeOn(mainScheduler)
                    .subscribe { router.newRootScreen(Screens.SIGN_IN_SCREEN) }
        }
    }

    fun isSignedIn() = accountRepository.isSingedIn()

    val accountDataChanges: Observable<AccountData>
        get() = accountRepository.accountDataChanges

    fun getAccountData() = accountRepository.getAccountData()

    fun updateAvatar(path: String) = accountRepository.updateAvatar(path)

    fun signOut() = accountRepository.signOut()
}