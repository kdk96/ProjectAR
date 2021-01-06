package com.kdk96.projectar.auth.di.auth

import com.github.terrakok.cicerone.Router
import com.kdk96.projectar.common.domain.resource.ResourceProvider
import com.kdk96.tanto.ComponentDependencies

interface AuthFlowDependencies : ComponentDependencies {
    fun router(): Router
    fun resourceProvider(): ResourceProvider
}
