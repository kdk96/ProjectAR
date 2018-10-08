package com.kdk96.projectar.di.auth

import android.content.Context
import com.kdk96.auth.data.repository.AuthApi
import com.kdk96.auth.data.storage.AuthHolder
import com.kdk96.auth.data.storage.Prefs
import dagger.Module
import dagger.Provides
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
}