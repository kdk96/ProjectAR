package com.kdk96.projectar.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.kdk96.projectar.R
import com.kdk96.tanto.android.inject
import javax.inject.Inject

class AppActivity : AppCompatActivity(R.layout.layout_fragment_container) {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    //    @Inject
//    lateinit var launcher: Launcher
//
    private val navigator: Navigator = AppNavigator(this, R.id.fragmentContainerView)

    //
//    private val currentFragment: BaseFragment?
//        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment
//
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectAR)
        inject()
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf(Replace(RootScreens.AuthFlow)))
        }

//        setContentView(R.layout.layout_container)
//        if (savedInstanceState == null) launcher.coldStart()
    }

    //
    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
//
//    override fun onBackPressed() {
//        currentFragment?.onBackPressed() ?: super.onBackPressed()
//    }
}
