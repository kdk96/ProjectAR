package com.kdk96.auth.di.signin

import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.auth.presentation.signin.SignInPresenter
import com.kdk96.auth.ui.SignInFragment
import com.kdk96.common.di.Component
import com.kdk96.common.di.Rx
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SignInScope

@SignInScope
@dagger.Component(
        modules = [SignInModule::class],
        dependencies = [SignInDependencies::class]
)
interface SignInComponent : Component {
    fun inject(signInFragment: SignInFragment)
}

@Module
object SignInModule {
    @Provides
    @JvmStatic
    @SignInScope
    fun provideSignInPresenter(authInteractor: AuthInteractor, router: Router, @Rx.MainThread mainThreadScheduler: Scheduler) =
            SignInPresenter(authInteractor, router, mainThreadScheduler)
}