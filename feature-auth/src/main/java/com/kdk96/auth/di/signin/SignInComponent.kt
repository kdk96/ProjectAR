package com.kdk96.auth.di.signin

import com.kdk96.auth.di.auth.ChildDependencies
import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.auth.presentation.signin.SignInPresenter
import com.kdk96.auth.ui.AuthFlowReachableScreens
import com.kdk96.auth.ui.SignInFragment
import com.kdk96.common.di.DaggerComponent
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@PerFragment
@Component(
        modules = [SignInModule::class],
        dependencies = [ChildDependencies::class]
)
interface SignInComponent : DaggerComponent {
    fun inject(signInFragment: SignInFragment)
}

@Module
object SignInModule {
    @Provides
    @JvmStatic
    @PerFragment
    fun provideSignInPresenter(
            authInteractor: AuthInteractor,
            flowRouter: FlowRouter,
            @Rx.MainThread mainThreadScheduler: Scheduler,
            screens: AuthFlowReachableScreens
    ) = SignInPresenter(authInteractor, flowRouter, mainThreadScheduler, screens)
}