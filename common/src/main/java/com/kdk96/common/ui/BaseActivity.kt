package com.kdk96.common.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.kdk96.common.di.Component
import com.kdk96.common.di.ComponentManager
import com.kdk96.common.di.HasComponent
import com.kdk96.common.di.getComponent
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder

abstract class BaseActivity : MvpAppCompatActivity(), HasComponent, ComponentManager {
    protected abstract val layoutRes: Int
    abstract val navigatorHolder: NavigatorHolder
    protected abstract val navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    protected inline fun <reified T : Component> getComponent() =
            getComponent<T>(application as ComponentManager, ::buildComponent)

    protected abstract fun buildComponent(): Component

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) clearComponent()
    }

    private fun clearComponent() {
        (application as ComponentManager).components.remove(this.javaClass)
    }
}