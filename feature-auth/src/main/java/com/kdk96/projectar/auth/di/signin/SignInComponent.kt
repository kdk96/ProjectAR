package com.kdk96.projectar.auth.di.signin

import com.kdk96.projectar.auth.domain.AuthDataValidator
import com.kdk96.projectar.auth.domain.AuthRepository
import com.kdk96.projectar.auth.ui.SignInFragment
import com.kdk96.projectar.common.domain.resource.ResourceProvider
import com.kdk96.tanto.ComponentDependencies
import com.kdk96.tanto.Injector
import com.kdk96.tanto.android.findComponentDependencies
import com.kdk96.tanto.injectorBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
object SingInComponentBuilderModule {
    @Provides
    @IntoMap
    @ClassKey(SignInFragment::class)
    fun provideBuilder() = injectorBuilder<SignInFragment> {
        DaggerSignInComponent.builder()
            .signInDependencies(findComponentDependencies())
            .build()
    }
}

@Singleton
@Component(dependencies = [SignInDependencies::class])
interface SignInComponent : Injector<SignInFragment>

interface SignInDependencies : ComponentDependencies {
    fun authDataValidator(): AuthDataValidator
    fun resourceProvider(): ResourceProvider
    fun authRepository(): AuthRepository
}
