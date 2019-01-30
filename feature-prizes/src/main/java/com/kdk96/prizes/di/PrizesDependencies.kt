package com.kdk96.prizes.di

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import io.reactivex.Scheduler
import retrofit2.Retrofit

interface PrizesDependencies : ComponentDependencies {
    fun retrofit(): Retrofit
    @Rx.Io
    fun ioScheduler(): Scheduler
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
    fun flowRouter(): FlowRouter
}