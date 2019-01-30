package com.kdk96.main.di

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import com.kdk96.database.Database
import com.kdk96.main.ui.MainFlowReachableScreens
import com.kdk96.settings.data.storage.AvatarFileProcessor
import com.kdk96.settings.domain.AccountInteractor
import io.reactivex.Scheduler
import retrofit2.Retrofit
import ru.terrakok.cicerone.Router

interface MainFlowDependencies : ComponentDependencies {
    fun router(): Router
    fun accountInteractor(): AccountInteractor
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
    @Rx.Io
    fun ioScheduler(): Scheduler
    fun retrofit(): Retrofit
    fun database(): Database
    fun mainFlowReachableScreens(): MainFlowReachableScreens
    fun avatarFileProcessor(): AvatarFileProcessor
}