package com.kdk96.auth.di.auth

import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.auth.ui.AuthFlowReachableScreens
import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import io.reactivex.Scheduler

interface ChildDependencies : ComponentDependencies {
    fun authInteractor(): AuthInteractor
    fun flowRouter(): FlowRouter
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
    fun screens(): AuthFlowReachableScreens
}