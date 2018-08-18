package com.kdk96.projectar.ui

import android.os.Bundle
import android.widget.Toast
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.BaseActivity
import com.kdk96.projectar.R
import com.kdk96.projectar.di.main.DaggerMainComponent
import com.kdk96.projectar.di.main.MainComponent
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportFragmentNavigator

class MainActivity : BaseActivity() {
    override val layoutRes = R.layout.container

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<MainComponent>().inject(this)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun buildComponent() = DaggerMainComponent.builder()
            .mainDependencies(findComponentDependencies())
            .build()

    override val navigator: Navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.container) {
        override fun exit() {
            finish()
        }

        override fun createFragment(screenKey: String?, data: Any?) = when (screenKey) {
            else -> null
        }

        override fun showSystemMessage(message: String?) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}