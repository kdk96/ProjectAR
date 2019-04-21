package com.kdk96.main.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kdk96.common.di.ComponentDependenciesProvider
import com.kdk96.common.di.HasChildDependencies
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.FlowFragment
import com.kdk96.common.ui.HasDrawerToggle
import com.kdk96.common.ui.setLaunchScreen
import com.kdk96.glide.GlideApp
import com.kdk96.main.R
import com.kdk96.main.di.DaggerMainFlowComponent
import com.kdk96.main.di.MainFlowComponent
import com.kdk96.main.presentation.MainPresenter
import com.kdk96.main.presentation.MainView
import com.kdk96.main.presentation.MainView.MenuItem
import com.kdk96.prizes.ui.PrizesFragment
import com.kdk96.quests.ui.QuestsFragment
import com.kdk96.settings.ui.SettingsFragment
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.navigation_view_header.view.*
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class MainFlowFragment : FlowFragment(), MainView, HasDrawerToggle, HasChildDependencies {
    override val layoutRes = R.layout.fragment_main

    init {
        componentBuilder = {
            DaggerMainFlowComponent.builder().mainFlowDependencies(findComponentDependencies()).build()
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    @Inject
    override lateinit var navigatorHolder: NavigatorHolder
    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<MainFlowComponent>().inject(this)
        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(MainFlowReachableScreens.Quests)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
        initSelectedItem()
    }

    private fun initSelectedItem() {
        childFragmentManager.executePendingTransactions()
        currentFragment?.let {
            when (it) {
                is QuestsFragment -> {
                    navigationView.setCheckedItem(R.id.quests)
                    MenuItem.QUESTS
                }
                is PrizesFragment -> {
                    navigationView.setCheckedItem(R.id.prizes)
                    MenuItem.PRIZES
                }
                is SettingsFragment -> {
                    navigationView.setCheckedItem(R.id.settings)
                    MenuItem.SETTINGS
                }
                else -> null
            }?.let { selectedItem -> presenter.initSelectedItem(selectedItem) }
        }
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

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun setupDrawerToggle(toolbar: Toolbar) {
        drawerToggle = ActionBarDrawerToggle(
            this.activity, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle!!)
        drawerToggle!!.syncState()
    }

    override fun removeDrawerToggle() {
        drawerLayout.removeDrawerListener(drawerToggle!!)
        drawerToggle = null
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }

    override fun onExit() = presenter.onExit()
}