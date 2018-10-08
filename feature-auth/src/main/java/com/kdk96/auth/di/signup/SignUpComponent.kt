package com.kdk96.auth.di.signup

import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.auth.presentation.signup.SignUpPresenter
import com.kdk96.auth.ui.SignUpFragment
import com.kdk96.common.di.Component
import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router

@PerFragment
@dagger.Component(
        modules = [SignUpModule::class],
        dependencies = [SignUpDependencies::class]
)
interface SignUpComponent : Component {
    @dagger.Component.Builder
    interface Builder {
        @BindsInstance
        fun email(email: String): Builder
        fun signUpDependencies(signUpDependencies: SignUpDependencies): Builder
        fun build(): SignUpComponent
    }
    fun inject(signUpFragment: SignUpFragment)
}

@Module
object SignUpModule {
    @Provides
    @JvmStatic
    @PerFragment
    fun provideSignUpPresenter(email: String, authInteractor: AuthInteractor, router: Router,
                               @Rx.MainThread mainThreadScheduler: Scheduler) =
            SignUpPresenter(email, authInteractor, router, mainThreadScheduler)
}

interface SignUpDependencies : ComponentDependencies {
    fun authInteractor(): AuthInteractor
    fun router(): Router
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
}