package com.kdk96.projectar

import android.app.Application
import com.arellomobile.mvp.RegisterMoxyReflectorPackages
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kdk96.common.di.ChildComponents
import com.kdk96.common.di.ComponentDependenciesProvider
import com.kdk96.common.di.ComponentManager
import com.kdk96.common.di.HasComponentDependencies
import com.kdk96.projectar.di.AppComponent
import com.kdk96.projectar.di.DaggerAppComponent
import javax.inject.Inject

@RegisterMoxyReflectorPackages(
        "com.kdk96.auth.screen",
        "com.kdk96.settings.screen",
        "com.kdk96.quests.screen",
        "com.kdk96.prizes.screen",
        "com.kdk96.questinfo.screen"
)
class App : Application(), ComponentManager, HasComponentDependencies {
    private lateinit var appComponent: AppComponent
    @Inject
    override lateinit var components: ChildComponents
    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        appComponent = DaggerAppComponent.builder().context(this).build()
        appComponent.inject(this)
    }
}