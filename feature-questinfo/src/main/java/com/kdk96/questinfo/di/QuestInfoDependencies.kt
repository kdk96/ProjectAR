package com.kdk96.questinfo.di

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import io.reactivex.Scheduler
import retrofit2.Retrofit

interface QuestInfoDependencies : ComponentDependencies {
    fun retrofit(): Retrofit
    fun id(): String
    fun flowRouter(): FlowRouter
    @Rx.Io
    fun ioScheduler(): Scheduler
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
}