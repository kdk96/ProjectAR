package com.kdk96.auth.ui

import android.os.Bundle
import com.kdk96.auth.di.auth.AuthFlowComponent
import com.kdk96.auth.di.auth.DaggerAuthFlowComponent
import com.kdk96.common.di.ComponentDependenciesProvider
import com.kdk96.common.di.HasChildDependencies
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.FlowFragment
import com.kdk96.common.ui.setLaunchScreen
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AuthFlowFragment : FlowFragment(), HasChildDependencies {
    init {
        componentBuilder = {
            DaggerAuthFlowComponent.builder()
                    .authFlowDependencies(findComponentDependencies())
                    .build()
        }
    }

    @Inject
    lateinit var router: Router
    @Inject
    override lateinit var navigatorHolder: NavigatorHolder
    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<AuthFlowComponent>().inject(this)
        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(AuthFlowReachableScreens.SignIn())
        }
    }

    override fun onExit() {
        router.exit()
    }
}