package com.kdk96.auth.di.auth

import com.kdk96.auth.data.repository.AuthApi
import com.kdk96.auth.data.repository.AuthRepository
import com.kdk96.auth.data.storage.AuthHolder
import com.kdk96.auth.di.signin.SignInDependencies
import com.kdk96.auth.di.signup.SignUpDependencies
import com.kdk96.auth.domain.AuthDataValidator
import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.common.di.Component
import com.kdk96.common.di.PerFeature
import com.kdk96.common.di.Rx
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@PerFeature
@dagger.Component(
        modules = [AuthModule::class],
        dependencies = [AuthDependencies::class]
)
interface AuthComponent : Component, SignInDependencies, SignUpDependencies

@Module
object AuthModule {
    @Provides
    @JvmStatic
    @PerFeature
    fun provideAuthRepository(authApi: AuthApi, @Rx.Io ioScheduler: Scheduler, authHolder: AuthHolder) =
            AuthRepository(authApi, ioScheduler, authHolder)

    @Provides
    @JvmStatic
    @PerFeature
    fun provideAuthDataValidator() = AuthDataValidator()

    @Provides
    @JvmStatic
    @PerFeature
    fun provideAuthInteractor(authDataValidator: AuthDataValidator, authRepository: AuthRepository) =
            AuthInteractor(authDataValidator, authRepository)
}
