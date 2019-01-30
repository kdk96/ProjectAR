package com.kdk96.quests.di

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.database.Database
import com.kdk96.quests.presenatation.QuestsReachableScreens
import io.reactivex.Scheduler
import retrofit2.Retrofit

interface QuestsDependencies : ComponentDependencies {
    fun flowRouter(): FlowRouter
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
    @Rx.Io
    fun ioScheduler(): Scheduler
    fun retrofit(): Retrofit
    fun database(): Database
    fun questsReachableScreens(): QuestsReachableScreens
}