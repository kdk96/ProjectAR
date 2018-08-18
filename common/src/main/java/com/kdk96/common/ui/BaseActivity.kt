package com.kdk96.common.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.kdk96.common.di.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity(), HasComponent, ComponentManager {
    protected abstract val layoutRes: Int
    @Inject
    override lateinit var components: ChildComponents
    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    abstract val navigator: Navigator

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
        if (isFinishing) clearComponent()
        super.onDestroy()
    }

    private fun clearComponent() {
        (application as ComponentManager).components.remove(this.javaClass)
    }
}