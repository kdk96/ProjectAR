package com.kdk96.projectar.di.main

import com.kdk96.common.di.PerActivity
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

@PerActivity
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
    @PerActivity
    fun provideChildComponents(): ChildComponents = mutableMapOf()

    @Provides
    @JvmStatic
    @PerActivity
    fun provideMainPresenter(router: Router, accountInteractor: AccountInteractor,
                             @Rx.MainThread mainThreadScheduler: Scheduler) =
            MainPresenter(router, accountInteractor, mainThreadScheduler)
}