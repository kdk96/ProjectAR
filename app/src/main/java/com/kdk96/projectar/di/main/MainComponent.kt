package com.kdk96.projectar.di.main

import com.kdk96.common.di.ActivityScope
import com.kdk96.common.di.ChildComponents
import com.kdk96.common.di.Component
import com.kdk96.common.di.Rx
import com.kdk96.projectar.presentation.MainPresenter
import com.kdk96.projectar.ui.MainActivity
import com.kdk96.settings.domain.AccountInteractor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router

@ActivityScope
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
    @ActivityScope
    fun provideChildComponents(): ChildComponents = mutableMapOf()

    @Provides
    @JvmStatic
    @ActivityScope
    fun provideMainPresenter(router: Router, accountInteractor: AccountInteractor,
                             @Rx.MainThread mainThreadScheduler: Scheduler) =
            MainPresenter(router, accountInteractor, mainThreadScheduler)
}