package com.kdk96.auth.di.signup

import com.kdk96.auth.di.auth.ChildDependencies
import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.auth.presentation.signup.SignUpPresenter
import com.kdk96.auth.ui.AuthFlowReachableScreens
import com.kdk96.auth.ui.SignUpFragment
import com.kdk96.common.di.DaggerComponent
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.common.presentation.FlowRouter
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@PerFragment
@Component(
        modules = [SignUpModule::class],
        dependencies = [ChildDependencies::class]
)
interface SignUpComponent : DaggerComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun email(email: String): Builder
        fun childDependencies(childDependencies: ChildDependencies): Builder
        fun build(): SignUpComponent
    }

    fun inject(signUpFragment: SignUpFragment)
}

@Module
object SignUpModule {
    @Provides
    @JvmStatic
    @PerFragment
    fun provideSignUpPresenter(
            email: String,
            authInteractor: AuthInteractor,
            flowRouter: FlowRouter,
            @Rx.MainThread mainThreadScheduler: Scheduler,
            screens: AuthFlowReachableScreens
    ) = SignUpPresenter(email, authInteractor, flowRouter, mainThreadScheduler, screens)
}