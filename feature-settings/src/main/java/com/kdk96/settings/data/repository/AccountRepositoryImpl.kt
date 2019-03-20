package com.kdk96.settings.data.repository

import com.kdk96.auth.data.storage.AuthHolder
import com.kdk96.auth.data.storage.Prefs
import com.kdk96.common.di.Rx
import com.kdk96.glide.GlideCacheCleaner
import com.kdk96.settings.data.network.AccountApi
import com.kdk96.settings.data.storage.AvatarFileProcessor
import com.kdk96.settings.domain.AccountData
import com.kdk96.settings.domain.AccountRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
        private val api: AccountApi,
        private val prefs: Prefs,
        @Rx.Io private val ioScheduler: Scheduler,
        private val authHolder: AuthHolder,
        private val avatarFileProcessor: AvatarFileProcessor,
        private val glideCacheCleaner: GlideCacheCleaner,
        private val databaseCleaner: DatabaseCleaner
) : AccountRepository {
    interface DatabaseCleaner {
        fun clean()
    }

    override val accountDataChanges = BehaviorSubject.create<AccountData>()

    override fun isSingedIn() = !authHolder.accessToken.isNullOrEmpty()

    override fun subscribeToRefreshFailure(listener: () -> Unit) =
            authHolder.registerRefreshFailureListener(listener)

    override fun getAccountData() = Single.just(AccountData(prefs.getEmail(), prefs.getName(), prefs.getPhotoUrl()))
            .concatWith(getDataFromServer())
            .doOnNext(accountDataChanges::onNext)
            .subscribeOn(ioScheduler)
            .ignoreElements()

    private fun getDataFromServer(): Single<AccountData> = api.getAccountInfo()
            .doOnSuccess { prefs.saveAccountData(it.email, it.name, it.photoUrl) }

    override fun updateAvatar(path: String) = Single.fromCallable { avatarFileProcessor.getCompressedImageFile(path) }
            .flatMap(::uploadAvatar)
            .doOnSuccess {
                prefs.saveAccountData(it.email, it.name, it.photoUrl)
                accountDataChanges.onNext(it)
            }
            .subscribeOn(ioScheduler)
            .ignoreElement()

    private fun uploadAvatar(compressedAvatarFile: File): Single<AccountData> {
        val requestBody = RequestBody.create(MediaType.parse("image/jpg"), compressedAvatarFile)
        val body = MultipartBody.Part.createFormData("avatar", compressedAvatarFile.name, requestBody)
        return api.updateAvatar(body).doFinally { compressedAvatarFile.delete() }
    }

    override fun signOut() = api.signOut()
            .onErrorComplete()
            .andThen(Completable.fromAction {
                authHolder.accessToken = null
                prefs.removeAccountData()
                databaseCleaner.clean()
                accountDataChanges.onNext(
                        AccountData("johndoe@gmail.com", "John Doe", null)
                )
            })
            .andThen(glideCacheCleaner.clearCache())
            .subscribeOn(ioScheduler)
}