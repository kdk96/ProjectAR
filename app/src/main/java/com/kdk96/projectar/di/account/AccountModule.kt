package com.kdk96.projectar.di.account

//import android.content.Context
//import com.kdk96.database.Database
//import com.kdk96.projectar.ui.RootScreens
//import com.kdk96.settings.data.network.AccountApi
//import com.kdk96.settings.data.repository.AccountRepositoryImpl
//import com.kdk96.settings.data.storage.AvatarFileProcessor
//import com.kdk96.settings.domain.AccountInteractor
//import com.kdk96.settings.domain.AccountInteractorImpl
//import com.kdk96.settings.domain.AccountRepository
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import retrofit2.Retrofit
//import ru.terrakok.cicerone.android.support.SupportAppScreen
//import javax.inject.Named
//import javax.inject.Singleton
//
//@Module
//abstract class AccountModule {
//    @Module
//    companion object {
//        @Provides
//        @JvmStatic
//        @Singleton
//        fun provideAvatarFileProcessor(context: Context) = AvatarFileProcessor(context)
//
//        @Provides
//        @JvmStatic
//        @Singleton
//        fun provideAccountApi(retrofit: Retrofit): AccountApi = retrofit.create(AccountApi::class.java)
//
//        @Provides
//        @JvmStatic
//        @Singleton
//        fun provideDatabaseCleaner(database: Database) = object : AccountRepositoryImpl.DatabaseCleaner {
//            override fun clean() {
//                database.clearAllTables()
//            }
//        }
//
//        @Provides
//        @Named("auth flow")
//        @JvmStatic
//        @Singleton
//        fun provideAuthFlowScreen(): SupportAppScreen = RootScreens.AuthFlow
//    }
//
//    @Binds
//    @Singleton
//    abstract fun provideAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository
//
//    @Binds
//    @Singleton
//    abstract fun provideAccountInteractor(accountInteractorImpl: AccountInteractorImpl): AccountInteractor
//}