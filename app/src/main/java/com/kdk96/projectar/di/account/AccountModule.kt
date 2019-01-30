package com.kdk96.projectar.di.account

import android.content.Context
import com.kdk96.auth.data.storage.AuthHolder
import com.kdk96.auth.data.storage.Prefs
import com.kdk96.common.di.Rx
import com.kdk96.database.Database
import com.kdk96.glide.GlideCacheCleaner
import com.kdk96.projectar.ui.RootScreens
import com.kdk96.settings.data.network.AccountApi
import com.kdk96.settings.data.repository.AccountRepository
import com.kdk96.settings.data.storage.AvatarFileProcessor
import com.kdk96.settings.domain.AccountInteractor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import retrofit2.Retrofit
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
object AccountModule {
    @Provides
    @JvmStatic
    @Singleton
    fun provideAvatarFileProcessor(context: Context) = AvatarFileProcessor(context)

    @Provides
    @JvmStatic
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi = retrofit.create(AccountApi::class.java)

    @Provides
    @JvmStatic
    @Singleton
    fun provideDatabaseCleaner(database: Database) = object : AccountRepository.DatabaseCleaner {
        override fun clean() {
            database.clearAllTables()
        }
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideAccountRepository(
            accountApi: AccountApi,
            prefs: Prefs,
            @Rx.Io ioScheduler: Scheduler,
            authHolder: AuthHolder,
            avatarFileProcessor: AvatarFileProcessor,
            glideCacheCleaner: GlideCacheCleaner,
            databaseCleaner: AccountRepository.DatabaseCleaner
    ) = AccountRepository(accountApi, prefs, ioScheduler, authHolder, avatarFileProcessor, glideCacheCleaner, databaseCleaner)

    @Provides
    @JvmStatic
    @Singleton
    fun provideAccountInteractor(
            accountRepository: AccountRepository,
            router: Router,
            @Rx.MainThread mainThreadScheduler: Scheduler
    ) = AccountInteractor(accountRepository, router, mainThreadScheduler, RootScreens.AuthFlow)
}