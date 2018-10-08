package com.kdk96.auth.di.signin

import com.kdk96.auth.di.AuthDependencies
import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.auth.presentation.signin.SignInPresenter
import com.kdk96.auth.ui.SignInFragment
import com.kdk96.common.di.Component
import com.kdk96.common.di.FragmentScope
import com.kdk96.common.di.Rx
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router

@FragmentScope
@dagger.Component(
        modules = [SignInModule::class],
        dependencies = [AuthDependencies::class]
)
interface SignInComponent : Component {
    fun inject(signInFragment: SignInFragment)
}

@Module
object SignInModule {
    @Provides
    @JvmStatic
    @FragmentScope
    fun provideSignInPresenter(authInteractor: AuthInteractor, router: Router, @Rx.MainThread mainThreadScheduler: Scheduler) =
            SignInPresenter(authInteractor, router, mainThreadScheduler)
}