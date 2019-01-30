package com.kdk96.quest.di

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import io.reactivex.Scheduler
import retrofit2.Retrofit
import ru.terrakok.cicerone.Router

interface QuestFlowDependencies : ComponentDependencies {
    fun retrofit(): Retrofit
    fun router(): Router
    @Rx.Io
    fun ioScheduler(): Scheduler
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
}