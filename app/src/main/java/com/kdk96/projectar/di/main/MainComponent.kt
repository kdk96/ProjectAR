package com.kdk96.projectar.di.main

import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.common.di.ChildComponents
import com.kdk96.common.di.Component
import com.kdk96.projectar.presentation.MainPresenter
import com.kdk96.projectar.ui.MainActivity
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
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

    @Provides
    @JvmStatic
    @MainActivityScope
    fun provideMainPresenter(router: Router, authInteractor: AuthInteractor) =
            MainPresenter(router, authInteractor)
}