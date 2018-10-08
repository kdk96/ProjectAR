package com.kdk96.projectar.di.main

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import com.kdk96.settings.domain.AccountInteractor
import io.reactivex.Scheduler
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

interface MainDependencies : ComponentDependencies {
    fun navigatorHolder(): NavigatorHolder
    fun router(): Router
    fun accountInteractor(): AccountInteractor
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
}