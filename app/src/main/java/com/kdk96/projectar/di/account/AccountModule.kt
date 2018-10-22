package com.kdk96.projectar.di.account

import android.content.Context
import com.kdk96.auth.data.storage.AuthHolder
import com.kdk96.auth.data.storage.Prefs
import com.kdk96.common.di.Rx
import com.kdk96.glide.GlideCacheCleaner
import com.kdk96.network.data.network.ApiAuthenticator
import com.kdk96.network.data.network.AuthHeaderInterceptor
import com.kdk96.network.data.network.ServerApi
import com.kdk96.settings.data.storage.AvatarFileProcessor
import com.kdk96.settings.data.repository.AccountRepository
import com.kdk96.settings.domain.AccountInteractor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
object AccountModule {
    private const val API_URL = "https://us-central1-projectar-960602.cloudfunctions.net/api/"

    @Provides
    @JvmStatic
    @Singleton
    fun provideAvatarFileProcessor(context: Context) = AvatarFileProcessor(context)

    @Provides
    @JvmStatic
    @Singleton
    fun provideServerApi(authHolder: AuthHolder): ServerApi {
        val httpClient = OkHttpClient.Builder()
                .addInterceptor(AuthHeaderInterceptor(authHolder))
                .authenticator(ApiAuthenticator(authHolder))
                .build()
        val retrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(ServerApi::class.java)
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideAccountRepository(
            serverApi: ServerApi,
            prefs: Prefs,
            @Rx.Io ioScheduler: Scheduler,
            authHolder: AuthHolder,
            avatarFileProcessor: AvatarFileProcessor,
            glideCacheCleaner: GlideCacheCleaner
    ) = AccountRepository(serverApi, prefs, ioScheduler, authHolder, avatarFileProcessor, glideCacheCleaner)

    @Provides
    @JvmStatic
    @Singleton
    fun provideAccountInteractor(
            accountRepository: AccountRepository,
            router: Router,
            @Rx.MainThread mainThreadScheduler: Scheduler
    ) = AccountInteractor(accountRepository, router, mainThreadScheduler)
}