package com.kdk96.settings.di

import com.kdk96.common.di.Component
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.settings.domain.AccountInteractor
import com.kdk96.settings.presentation.SettingsPresenter
import com.kdk96.settings.ui.SettingsFragment
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router

@PerFragment
@dagger.Component(
        modules = [SettingsModule::class],
        dependencies = [SettingsDependencies::class]
)
interface SettingsComponent : Component {
    fun inject(settingsFragment: SettingsFragment)
}

@Module
object SettingsModule {
    @Provides
    @JvmStatic
    @PerFragment
    fun provideSettingsPresenter(
            router: Router,
            accountInteractor: AccountInteractor,
            @Rx.MainThread mainThreadScheduler: Scheduler
    ) = SettingsPresenter(router, accountInteractor, mainThreadScheduler)
}