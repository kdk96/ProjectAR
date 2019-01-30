package com.kdk96.projectar.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.kdk96.common.ui.BaseFragment
import com.kdk96.projectar.App
import com.kdk96.projectar.R
import com.kdk96.projectar.presentation.Launcher
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class AppActivity : MvpAppCompatActivity() {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    @Inject
    lateinit var launcher: Launcher

    private val navigator: Navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {}

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_container)
        if (savedInstanceState == null) launcher.coldStart()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }
}