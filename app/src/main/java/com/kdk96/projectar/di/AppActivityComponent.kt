package com.kdk96.projectar.di

import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.kdk96.projectar.ui.AppActivity
import com.kdk96.tanto.ComponentDependencies
import com.kdk96.tanto.Injector
import com.kdk96.tanto.android.findComponentDependencies
import com.kdk96.tanto.injectorBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
object AppActivityComponentBuilderModule {
    @Provides
    @IntoMap
    @ClassKey(AppActivity::class)
    fun builder() = injectorBuilder<AppActivity> {
        DaggerAppActivityComponent.builder()
            .appActivityDependencies(findComponentDependencies())
            .build()
    }
}

@Singleton
@Component(dependencies = [AppActivityDependencies::class])
interface AppActivityComponent : Injector<AppActivity>

interface AppActivityDependencies : ComponentDependencies {
    fun navigatorHolder(): NavigatorHolder
    fun router(): Router
}
