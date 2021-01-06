package com.kdk96.projectar.auth.di.auth

import com.kdk96.projectar.auth.data.repository.AuthRepositoryImpl
import com.kdk96.projectar.auth.di.signin.SignInDependencies
import com.kdk96.projectar.auth.di.signin.SingInComponentBuilderModule
import com.kdk96.projectar.auth.domain.AuthRepository
import com.kdk96.projectar.auth.ui.AuthFlowFragment
import com.kdk96.projectar.common.di.FlowNavigationModule
import com.kdk96.tanto.Injector
import com.kdk96.tanto.android.findComponentDependencies
import com.kdk96.tanto.injectorBuilder
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(
    includes = [
        SingInComponentBuilderModule::class,
//        SignUpComponentBuilderModule::class
    ]
)
object AuthFlowComponentBuilderModule {
    @Provides
    @IntoMap
    @ClassKey(AuthFlowFragment::class)
    fun provideBuilder() = injectorBuilder<AuthFlowFragment> {
        DaggerAuthFlowComponent.builder()
            .authFlowDependencies(findComponentDependencies())
            .build()
    }
}

@Singleton
@Component(
    modules = [
        FlowNavigationModule::class,
        AuthModule::class
    ],
    dependencies = [AuthFlowDependencies::class]
)
interface AuthFlowComponent : Injector<AuthFlowFragment>, SignInDependencies

@Module
interface AuthModule {
    @Binds
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}

//@Module
//interface ChildDependenciesModule {
//    @Binds
//    @IntoMap
//    @ComponentDependenciesKey(ChildDependencies::class)
//    fun provideChildDependencies(authFlowComponent: AuthFlowComponent): ComponentDependencies
//}
