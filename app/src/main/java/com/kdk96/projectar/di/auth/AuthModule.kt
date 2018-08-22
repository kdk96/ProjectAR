package com.kdk96.projectar.di.auth

import android.content.Context
import com.kdk96.auth.data.repository.AuthApi
import com.kdk96.auth.data.repository.AuthRepository
import com.kdk96.auth.data.storage.AuthHolder
import com.kdk96.auth.data.storage.Prefs
import com.kdk96.auth.domain.AuthDataValidator
import com.kdk96.auth.domain.AuthInteractor
import com.kdk96.common.di.Rx
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Singleton

@Module
object AuthModule {
    @Provides
    @JvmStatic
    @Singleton
    fun providePrefs(context: Context) = Prefs(context)

    @Provides
    @JvmStatic
    @Singleton
    fun provideAuthHolder(prefs: Prefs, authApi: AuthApi) = AuthHolder(prefs, authApi)

    @Provides
    @JvmStatic
    @Singleton
    fun provideAuthRepository(authApi: AuthApi, @Rx.Io ioScheduler: Scheduler, authHolder: AuthHolder) =
            AuthRepository(authApi, ioScheduler, authHolder)

    @Provides
    @JvmStatic
    @Singleton
    fun provideAuthDataValidator() = AuthDataValidator()

    @Provides
    @JvmStatic
    @Singleton
    fun provideAuthInteractor(authDataValidator: AuthDataValidator, authRepository: AuthRepository) =
            AuthInteractor(authDataValidator, authRepository)
}