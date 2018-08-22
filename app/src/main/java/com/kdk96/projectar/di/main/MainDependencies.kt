package com.kdk96.projectar.di.main

import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.common.di.ComponentDependencies
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

interface MainDependencies : ComponentDependencies {
    fun navigatorHolder(): NavigatorHolder
    fun router(): Router
    fun authInteractor(): AuthInteractor
}