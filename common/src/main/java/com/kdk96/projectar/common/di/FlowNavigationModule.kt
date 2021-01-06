package com.kdk96.projectar.common.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.kdk96.projectar.common.presentation.FlowRouter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object FlowNavigationModule {

    @Provides
    @Singleton
    fun provideCicerone(router: Router) = Cicerone.create(FlowRouter(router))

    @Provides
    @Singleton
    fun provideFlowRouter(cicerone: Cicerone<FlowRouter>) = cicerone.router

    @Provides
    @Singleton
    fun provideFlowNavigatorHolder(cicerone: Cicerone<FlowRouter>) = cicerone.getNavigatorHolder()
}
