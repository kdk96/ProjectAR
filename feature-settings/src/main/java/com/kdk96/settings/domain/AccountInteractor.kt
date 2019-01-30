package com.kdk96.settings.domain

import com.kdk96.settings.data.repository.AccountRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen

class AccountInteractor(
        private val accountRepository: AccountRepository,
        private val router: Router,
        private val mainScheduler: Scheduler,
        private val authFlowScreen: SupportAppScreen
) {
    init {
        accountRepository.subscribeToRefreshFailure {
            signOut().observeOn(mainScheduler)
                    .subscribe { router.newRootScreen(authFlowScreen) }
        }
    }

    fun signOut() = accountRepository.signOut()

    fun isSignedIn() = accountRepository.isSingedIn()

    fun getAccountData() = accountRepository.getAccountData()

    val accountDataChanges: Observable<AccountData>
        get() = accountRepository.accountDataChanges

    fun updateAvatar(path: String) = accountRepository.updateAvatar(path)
}