package com.kdk96.auth.data.network

import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val url = originalUrl.newBuilder().addQueryParameter("key", apiKey).build()
        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}