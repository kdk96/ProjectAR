package com.kdk96.projectar.ui

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
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
import com.kdk96.common.ui.HasDrawerToggle
import com.kdk96.glide.GlideApp
import com.kdk96.prizes.ui.PrizesFragment
import com.kdk96.projectar.R
import com.kdk96.projectar.di.main.DaggerMainComponent
import com.kdk96.projectar.di.main.MainComponent
import com.kdk96.projectar.presentation.MainPresenter
import com.kdk96.projectar.presentation.MainView
import com.kdk96.projectar.presentation.MainView.MenuItem
import com.kdk96.quests.ui.QuestsFragment
import com.kdk96.settings.ui.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_view_header.view.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView, HasDrawerToggle {
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
    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<MainComponent>().inject(this)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.quests -> MenuItem.QUESTS
                R.id.prizes -> MenuItem.PRIZES
                R.id.settings -> MenuItem.SETTINGS
                else -> null
            }?.let { presenter.onMenuItemSelected(it) }
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
            Screens.QUESTS_SCREEN -> {
                navigationView.setCheckedItem(R.id.quests)
                QuestsFragment()
            }
            Screens.PRIZES_SCREEN -> PrizesFragment()
            Screens.SETTINGS_SCREEN -> SettingsFragment()
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
            is QuestsFragment, is SettingsFragment, is PrizesFragment -> true
            else -> false
        }
        enableNavigationDrawer(enable)
    }

    private fun enableNavigationDrawer(enable: Boolean) {
        val lockMode = if (enable) {
            presenter.onDrawerUnlocked()
            DrawerLayout.LOCK_MODE_UNLOCKED
        } else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        drawerLayout.setDrawerLockMode(lockMode, GravityCompat.START)
    }

    override fun setupDrawerToggle(toolbar: Toolbar) {
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(drawerToggle!!)
        drawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun removeDrawerToggle() {
        drawerLayout.removeDrawerListener(drawerToggle!!)
        drawerToggle = null
    }

    override fun updateAccountInfo(name: String, email: String, avatarUrl: String?) {
        val headerView = navigationView.getHeaderView(0)
        headerView.nameTV.text = name
        headerView.emailTV.text = email
        GlideApp.with(this)
                .load(avatarUrl)
                .placeholder(R.drawable.avatar_placeholder)
                .circleCrop()
                .into(headerView.avatarIV)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) clearComponent<MainComponent>()
    }
}