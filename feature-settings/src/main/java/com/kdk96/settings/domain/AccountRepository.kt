package com.kdk96.settings.domain

import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject

interface AccountRepository {
    val accountDataChanges: BehaviorSubject<AccountData>
    fun isSingedIn(): Boolean
    fun subscribeToRefreshFailure(listener: () -> Unit)
    fun getAccountData(): Completable
    fun updateAvatar(path: String): Completable
    fun signOut(): Completable
}