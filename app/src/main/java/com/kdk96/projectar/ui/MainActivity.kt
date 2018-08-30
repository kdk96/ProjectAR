package com.kdk96.projectar.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kdk96.auth.ui.SignInFragment
import com.kdk96.auth.ui.SignUpFragment
import com.kdk96.common.di.ChildComponents
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.presentation.Screens
import com.kdk96.common.ui.BaseActivity
import com.kdk96.common.ui.BaseFragment
import com.kdk96.projectar.R
import com.kdk96.projectar.di.main.DaggerMainComponent
import com.kdk96.projectar.di.main.MainComponent
import com.kdk96.projectar.presentation.MainPresenter
import com.kdk96.projectar.presentation.MainView
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {
    override val layoutRes = R.layout.activity_main

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as BaseFragment?

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    @Inject
    override lateinit var navigatorHolder: NavigatorHolder
    @Inject
    override lateinit var components: ChildComponents

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<MainComponent>().inject(this)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun buildComponent() = DaggerMainComponent.builder()
            .mainDependencies(findComponentDependencies())
            .build()

    override val navigator: Navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.container) {
        override fun applyCommands(commands: Array<out Command>?) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
            updateNavigationDrawer()
        }

        override fun createFragment(screenKey: String?, data: Any?): Fragment? = when (screenKey) {
            Screens.SIGN_IN_SCREEN -> SignInFragment()
            Screens.SIGN_UP_SCREEN -> SignUpFragment.newInstance(data as String)
            else -> null
        }

        override fun showSystemMessage(message: String?) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }

        override fun exit() {
            finish()
        }
    }

    private fun updateNavigationDrawer() {
        val enable = when (currentFragment) {
            else -> false
        }
        enableNavigationDrawer(enable)
    }

    private fun enableNavigationDrawer(enable: Boolean) {
        val lockMode = if (enable) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        drawerLayout.setDrawerLockMode(lockMode, GravityCompat.START)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else currentFragment?.onBackPressed() ?: super.onBackPressed()
    }
}