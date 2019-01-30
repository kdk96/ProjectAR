package com.kdk96.main.di

import com.kdk96.common.di.*
import com.kdk96.common.presentation.FlowRouter
import com.kdk96.main.presentation.MainPresenter
import com.kdk96.main.ui.MainFlowFragment
import com.kdk96.main.ui.MainFlowReachableScreens
import com.kdk96.prizes.di.PrizesDependencies
import com.kdk96.quests.di.QuestsDependencies
import com.kdk96.quests.presenatation.QuestsReachableScreens
import com.kdk96.settings.di.SettingsDependencies
import com.kdk96.settings.domain.AccountInteractor
import com.kdk96.settings.presentation.SettingsReachableScreens
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.Scheduler

@PerFlow
@Component(
        modules = [FlowNavigationModule::class, MainModule::class, ChildDependenciesModule::class],
        dependencies = [MainFlowDependencies::class])
interface MainFlowComponent : DaggerComponent, QuestsDependencies, PrizesDependencies, SettingsDependencies {
    fun inject(mainFlowFragment: MainFlowFragment)
}

@Module
object MainModule {
    @Provides
    @JvmStatic
    @PerFlow
    fun providePresenter(
            accountInteractor: AccountInteractor,
            @Rx.MainThread mainThreadScheduler: Scheduler,
            flowRouter: FlowRouter
    ) = MainPresenter(accountInteractor, mainThreadScheduler, flowRouter)
}

@Module
interface ChildDependenciesModule {
    @Binds
    fun provideQuestsReachableScreens(mainFlowReachableScreens: MainFlowReachableScreens): QuestsReachableScreens

    @Binds
    fun provideSettingsReachableScreens(mainFlowReachableScreens: MainFlowReachableScreens): SettingsReachableScreens

    @Binds
    @IntoMap
    @ComponentDependenciesKey(QuestsDependencies::class)
    fun provideQuestsDependencies(mainFlowComponent: MainFlowComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(PrizesDependencies::class)
    fun providePrizesDependencies(mainFlowComponent: MainFlowComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(SettingsDependencies::class)
    fun provideSettingsDependencies(mainFlowComponent: MainFlowComponent): ComponentDependencies
}