package com.kdk96.projectar.data.network

import com.kdk96.auth.data.storage.AuthHolder
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class ApiAuthenticator(private val authHolder: AuthHolder) : Authenticator {
    companion object {
        const val AUTH_HEADER = "Authorization"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        var accessToken: String? = null
        val requestToken = response.request().header(AUTH_HEADER)
        synchronized(this) {
            accessToken = authHolder.accessToken
            if ("Bearer $accessToken" == requestToken) {
                authHolder.refresh()
                accessToken = authHolder.accessToken
            }
        }
        accessToken?.let {
            return response.request().newBuilder().addHeader(AUTH_HEADER, "Bearer $it").build()
        } ?: return null
    }
}