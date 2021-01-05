package com.kdk96.projectar.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.kdk96.projectar.App
import com.kdk96.projectar.R
import com.kdk96.tanto.Tanto
import com.kdk96.tanto.android.inject
import javax.inject.Inject

class AppActivity : AppCompatActivity(R.layout.layout_fragment_container) {
//    @Inject
//    lateinit var navigatorHolder: NavigatorHolder
//    @Inject
//    lateinit var launcher: Launcher
//
//    private val navigator: Navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {
//        override fun setupFragmentTransaction(
//                command: Command?,
//                currentFragment: Fragment?,
//                nextFragment: Fragment?,
//                fragmentTransaction: FragmentTransaction
//        ) {
//            fragmentTransaction.setReorderingAllowed(true)
//        }
//    }
//
//    private val currentFragment: BaseFragment?
//        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment
//
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectAR)
//        inject()
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.layout_container)
//        if (savedInstanceState == null) launcher.coldStart()
    }
//
//    override fun onResumeFragments() {
//        super.onResumeFragments()
//        navigatorHolder.setNavigator(navigator)
//    }
//
//    override fun onPause() {
//        navigatorHolder.removeNavigator()
//        super.onPause()
//    }
//
//    override fun onBackPressed() {
//        currentFragment?.onBackPressed() ?: super.onBackPressed()
//    }
}