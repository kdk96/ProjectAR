package com.kdk96.projectar.data.network

import com.kdk96.auth.data.storage.AuthHolder
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor(private val authHolder: AuthHolder) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        authHolder.accessToken?.let {
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer ${authHolder.accessToken}")
                    .build()
        }
        return chain.proceed(request)
    }
}