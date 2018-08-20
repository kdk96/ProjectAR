package com.kdk96.auth.di.signin

import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.Rx
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router

interface SignInDependencies : ComponentDependencies {
    fun authInteractor(): AuthInteractor
    fun router(): Router
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
}