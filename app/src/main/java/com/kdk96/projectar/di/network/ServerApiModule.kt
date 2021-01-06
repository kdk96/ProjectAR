package com.kdk96.projectar.di.network

//import com.kdk96.projectar.auth.data.storage.AuthHolder
//import com.kdk96.projectar.data.network.ApiAuthenticator
//import com.kdk96.projectar.data.network.AuthHeaderInterceptor
//import dagger.Module
//import dagger.Provides
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//object ServerApiModule {
//    private const val API_URL = "https://us-central1-projectar-960602.cloudfunctions.net/api/"
//
//    @Provides
//    @JvmStatic
//    @Singleton
//    fun provideRetrofit(authHolder: AuthHolder): Retrofit {
//        val httpClient = OkHttpClient.Builder()
//                .addInterceptor(AuthHeaderInterceptor(authHolder))
//                .authenticator(ApiAuthenticator(authHolder))
//                .build()
//        return Retrofit.Builder()
//                .client(httpClient)
//                .baseUrl(API_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build()
//    }
//}