package com.kdk96.auth.di.auth

import com.kdk96.auth.data.repository.AuthApi
import com.kdk96.auth.data.repository.AuthRepository
import com.kdk96.auth.data.storage.AuthHolder
import com.kdk96.auth.domain.AuthDataValidator
import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.auth.ui.AuthFlowFragment
import com.kdk96.common.di.*
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.Scheduler

@PerFlow
@Component(
        modules = [FlowNavigationModule::class, AuthModule::class, ChildDependenciesModule::class],
        dependencies = [AuthFlowDependencies::class]
)
interface AuthFlowComponent : DaggerComponent, ChildDependencies {
    fun inject(authFlowFragment: AuthFlowFragment)
}

@Module
object AuthModule {
    @Provides
    @JvmStatic
    @PerFlow
    fun provideAuthRepository(
            authApi: AuthApi,
            @Rx.Io ioScheduler: Scheduler,
            authHolder: AuthHolder
    ) = AuthRepository(authApi, ioScheduler, authHolder)

    @Provides
    @JvmStatic
    @PerFlow
    fun provideAuthDataValidator() = AuthDataValidator()

    @Provides
    @JvmStatic
    @PerFlow
    fun provideAuthInteractor(
            authDataValidator: AuthDataValidator,
            authRepository: AuthRepository
    ) = AuthInteractor(authDataValidator, authRepository)
}

@Module
interface ChildDependenciesModule {
    @Binds
    @IntoMap
    @ComponentDependenciesKey(ChildDependencies::class)
    fun provideChildDependencies(authFlowComponent: AuthFlowComponent): ComponentDependencies
}