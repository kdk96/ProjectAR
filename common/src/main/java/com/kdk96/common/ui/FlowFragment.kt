package com.kdk96.common.ui

import com.kdk96.common.R
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

abstract class FlowFragment : BaseFragment() {
    override val layoutRes = R.layout.layout_container

    protected val currentFragment
        get() = childFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    abstract val navigatorHolder: NavigatorHolder

    protected val navigator: Navigator by lazy {
        object : SupportAppNavigator(this.activity, childFragmentManager, R.id.container) {
            override fun activityBack() = onExit()
        }
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: onExit()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    abstract fun onExit()
}