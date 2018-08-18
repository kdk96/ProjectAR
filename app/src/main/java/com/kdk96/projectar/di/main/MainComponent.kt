package com.kdk96.projectar.di.main

import com.kdk96.common.di.ChildComponents
import com.kdk96.common.di.Component
import com.kdk96.projectar.ui.MainActivity
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MainActivityScope

@MainActivityScope
@dagger.Component(
        dependencies = [MainDependencies::class],
        modules = [MainModule::class]
)
interface MainComponent : Component {
    fun inject(mainActivity: MainActivity)
}

@Module
object MainModule {
    @Provides
    @JvmStatic
    @MainActivityScope
    fun provideChildComponents(): ChildComponents = mutableMapOf()
}