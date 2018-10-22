package com.kdk96.settings.data.repository

import com.kdk96.auth.data.storage.AuthHolder
import com.kdk96.auth.data.storage.Prefs
import com.kdk96.common.di.Rx
import com.kdk96.glide.GlideCacheCleaner
import com.kdk96.network.data.network.ServerApi
import com.kdk96.network.domain.AccountData
import com.kdk96.settings.data.storage.AvatarFileProcessor
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AccountRepository(
        private val api: ServerApi,
        private val prefs: Prefs,
        @Rx.Io private val ioScheduler: Scheduler,
        private val authHolder: AuthHolder,
        private val avatarFileProcessor: AvatarFileProcessor,
        private val glideCacheCleaner: GlideCacheCleaner
) {
    val accountDataChanges = BehaviorSubject.create<AccountData>()

    fun isSingedIn() = !authHolder.accessToken.isNullOrEmpty()

    fun subscribeToRefreshFailure(listener: () -> Unit) {
        authHolder.registerRefreshFailureListener(listener)
    }

    fun getAccountData() = Single.concat(
            Single.just(AccountData(prefs.getEmail(), prefs.getName(), prefs.getPhotoUrl())),
            getDataFromServer()
    )
            .doOnNext(accountDataChanges::onNext)
            .subscribeOn(ioScheduler)
            .ignoreElements()

    private fun getDataFromServer(): Single<AccountData> = api.getAccountInfo()
            .doOnSuccess { prefs.saveAccountData(it.email, it.name, it.photoUrl) }

    fun updateAvatar(path: String) = Single.fromCallable { avatarFileProcessor.getCompressedImageFile(path) }
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

    fun signOut() = api.signOut()
            .onErrorComplete()
            .andThen(Completable.fromAction {
                authHolder.accessToken = null
                prefs.removeAccountData()
                accountDataChanges.onNext(
                        AccountData("johndoe@gmail.com", "John Doe", null)
                )
            })
            .andThen(glideCacheCleaner.clearCache())
            .subscribeOn(ioScheduler)
}