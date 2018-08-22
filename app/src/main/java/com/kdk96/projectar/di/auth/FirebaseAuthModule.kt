package com.kdk96.projectar.di.auth

import android.content.Context
import com.kdk96.auth.data.network.FirebaseAuthApi
import com.kdk96.auth.data.network.FirebaseAuthImpl
import com.kdk96.auth.data.network.FirebaseRefreshApi
import com.kdk96.auth.data.network.KeyInterceptor
import com.kdk96.auth.data.repository.AuthApi
import com.kdk96.auth.firebase.R
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object FirebaseAuthModule {
    private const val URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/"
    private const val REFRESH_URL = "https://securetoken.googleapis.com/v1/"

    @Provides
    @JvmStatic
    @Singleton
    fun provideFirebaseAuthApi(context: Context): AuthApi {
        val apiKey = context.getString(R.string.api_key)
        val httpClient = OkHttpClient.Builder()
                .addInterceptor(KeyInterceptor(apiKey))
                .build()
        val retrofitBuilder = Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
        val authApi = retrofitBuilder.baseUrl(URL).build().create(FirebaseAuthApi::class.java)
        val refreshApi = retrofitBuilder.baseUrl(REFRESH_URL).build().create(FirebaseRefreshApi::class.java)
        return FirebaseAuthImpl(authApi, refreshApi)
    }
}