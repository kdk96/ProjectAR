package com.kdk96.quests.di

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import com.kdk96.database.Database
import io.reactivex.Scheduler
import retrofit2.Retrofit
import ru.terrakok.cicerone.Router

interface QuestsDependencies : ComponentDependencies {
    fun router(): Router
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
    @Rx.Io
    fun ioScheduler(): Scheduler
    fun retrofit(): Retrofit
    fun database(): Database
}