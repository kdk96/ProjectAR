package com.kdk96.common.di

import com.kdk96.common.presentation.FlowRouter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Module
object FlowNavigationModule {
    @Provides
    @JvmStatic
    @PerFlow
    fun provideCicerone(router: Router) = Cicerone.create(FlowRouter(router))

    @Provides
    @JvmStatic
    @PerFlow
    fun provideFlowRouter(cicerone: Cicerone<FlowRouter>) = cicerone.router

    @Provides
    @JvmStatic
    @PerFlow
    fun provideFlowNavigatorHolder(cicerone: Cicerone<FlowRouter>) = cicerone.navigatorHolder
}