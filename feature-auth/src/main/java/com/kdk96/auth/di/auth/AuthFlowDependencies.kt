package com.kdk96.auth.di.auth

import com.kdk96.auth.data.repository.AuthApi
import com.kdk96.auth.data.storage.AuthHolder
import com.kdk96.auth.ui.AuthFlowReachableScreens
import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router

interface AuthFlowDependencies : ComponentDependencies {
    fun authHolder(): AuthHolder
    fun authApi(): AuthApi
    @Rx.Io
    fun ioScheduler(): Scheduler
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
    fun router(): Router
    fun screens(): AuthFlowReachableScreens
}