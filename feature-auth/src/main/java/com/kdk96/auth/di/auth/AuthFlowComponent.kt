package com.kdk96.auth.di.auth

import com.kdk96.auth.data.repository.AuthRepositoryImpl
import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.auth.domain.AuthInteractorImpl
import com.kdk96.auth.domain.AuthRepository
import com.kdk96.auth.ui.AuthFlowFragment
import com.kdk96.common.di.*
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@PerFlow
@Component(
        modules = [FlowNavigationModule::class, AuthModule::class, ChildDependenciesModule::class],
        dependencies = [AuthFlowDependencies::class]
)
interface AuthFlowComponent : DaggerComponent, ChildDependencies {
    fun inject(authFlowFragment: AuthFlowFragment)
}

@Module
interface AuthModule {
    @Binds
    @PerFlow
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @PerFlow
    fun provideAuthInteractor(authInteractorImpl: AuthInteractorImpl): AuthInteractor
}

@Module
interface ChildDependenciesModule {
    @Binds
    @IntoMap
    @ComponentDependenciesKey(ChildDependencies::class)
    fun provideChildDependencies(authFlowComponent: AuthFlowComponent): ComponentDependencies
}