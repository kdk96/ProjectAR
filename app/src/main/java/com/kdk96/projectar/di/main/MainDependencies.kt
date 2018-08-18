package com.kdk96.projectar.di.main

import com.kdk96.common.di.ComponentDependencies
import ru.terrakok.cicerone.NavigatorHolder

interface MainDependencies : ComponentDependencies {
    fun navigatorHolder(): NavigatorHolder
}