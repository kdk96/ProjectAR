package com.kdk96.questinfo.di

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import com.kdk96.questinfo.presentation.QuestInfoReachableScreens
import io.reactivex.Scheduler
import retrofit2.Retrofit
import ru.terrakok.cicerone.Router

interface QuestInfoDependencies : ComponentDependencies {
    fun retrofit(): Retrofit
    fun router(): Router
    fun questInfoReachableScreens(): QuestInfoReachableScreens
    @Rx.Io
    fun ioScheduler(): Scheduler
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
}