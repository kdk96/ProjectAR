package com.kdk96.auth.di.signup

import com.kdk96.auth.di.AuthDependencies
import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.auth.presentation.signup.SignUpPresenter
import com.kdk96.auth.ui.SignUpFragment
import com.kdk96.common.di.Component
import com.kdk96.common.di.Rx
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SignUpScope

@SignUpScope
@dagger.Component(
        modules = [SignUpModule::class],
        dependencies = [AuthDependencies::class]
)
interface SignUpComponent : Component {
    @dagger.Component.Builder
    interface Builder {
        @BindsInstance
        fun email(email: String): Builder

        fun authDependencies(authDependencies: AuthDependencies): Builder
        fun build(): SignUpComponent
    }

    fun inject(signUpFragment: SignUpFragment)
}

@Module
object SignUpModule {
    @Provides
    @JvmStatic
    @SignUpScope
    fun provideSignUpPresenter(email: String, interactor: AuthInteractor, router: Router, @Rx.MainThread mainThreadScheduler: Scheduler) =
            SignUpPresenter(email, interactor, router, mainThreadScheduler)
}