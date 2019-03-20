package com.kdk96.settings.domain

import com.kdk96.common.di.Rx
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject
import javax.inject.Named

interface AccountInteractor {
    val accountDataChanges: Observable<AccountData>
    fun signOut(): Completable
    fun isSignedIn(): Boolean
    fun getAccountData(): Completable
    fun updateAvatar(path: String): Completable
}

class AccountInteractorImpl @Inject constructor(
        private val accountRepository: AccountRepository,
        private val router: Router,
        @Rx.MainThread private val mainScheduler: Scheduler,
        @Named("auth flow") private val authFlowScreen: SupportAppScreen
) : AccountInteractor {
    init {
        accountRepository.subscribeToRefreshFailure {
            signOut().observeOn(mainScheduler)
                    .subscribe { router.newRootScreen(authFlowScreen) }
        }
    }

    override fun signOut() = accountRepository.signOut()

    override fun isSignedIn() = accountRepository.isSingedIn()

    override fun getAccountData() = accountRepository.getAccountData()

    override val accountDataChanges: Observable<AccountData>
        get() = accountRepository.accountDataChanges

    override fun updateAvatar(path: String) = accountRepository.updateAvatar(path)
}