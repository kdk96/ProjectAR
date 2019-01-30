package com.kdk96.settings.di

import com.kdk96.common.di.DaggerComponent
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.settings.domain.AccountInteractor
import com.kdk96.settings.presentation.SettingsPresenter
import com.kdk96.settings.presentation.SettingsReachableScreens
import com.kdk96.settings.ui.SettingsFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@PerFragment
@Component(
        modules = [SettingsModule::class],
        dependencies = [SettingsDependencies::class]
)
interface SettingsComponent : DaggerComponent {
    fun inject(settingsFragment: SettingsFragment)
}

@Module
object SettingsModule {
    @Provides
    @JvmStatic
    @PerFragment
    fun provideSettingsPresenter(
            flowRouter: FlowRouter,
            accountInteractor: AccountInteractor,
            @Rx.MainThread mainThreadScheduler: Scheduler,
            screens: SettingsReachableScreens
    ) = SettingsPresenter(flowRouter, accountInteractor, mainThreadScheduler, screens)
}