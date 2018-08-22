package com.kdk96.projectar.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kdk96.auth.presentation.AuthRoutes
import com.kdk96.auth.ui.SignInFragment
import com.kdk96.auth.ui.SignUpFragment
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.BaseActivity
import com.kdk96.projectar.R
import com.kdk96.projectar.di.main.DaggerMainComponent
import com.kdk96.projectar.di.main.MainComponent
import com.kdk96.projectar.presentation.MainPresenter
import com.kdk96.projectar.presentation.MainRoutes
import com.kdk96.projectar.presentation.MainView
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {
    override val layoutRes = R.layout.container

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<MainComponent>().inject(this)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun buildComponent() = DaggerMainComponent.builder()
            .mainDependencies(findComponentDependencies())
            .build()

    override val navigator: Navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.container) {
        override fun createFragment(screenKey: String?, data: Any?): Fragment? = when (screenKey) {
            MainRoutes.SIGN_IN_SCREEN -> SignInFragment()
            AuthRoutes.SIGN_UP_SCREEN -> SignUpFragment.newInstance(data as String)
            else -> null
        }

        override fun showSystemMessage(message: String?) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }

        override fun exit() {
            finish()
        }
    }
}