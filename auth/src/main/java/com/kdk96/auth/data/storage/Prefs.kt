package com.kdk96.auth.data.storage

import android.content.Context
import javax.inject.Inject

class Prefs @Inject constructor(private val context: Context) {
    companion object {
        private const val AUTH_DATA = "auth_data"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_EMAIL = "email"
        private const val KEY_NAME = "name"
    }

    private fun getSharedPreferences(prefsName: String) =
            context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun saveAuthData(accessToken: String, refreshToken: String, email: String, name: String) =
            getSharedPreferences(AUTH_DATA).edit()
                    .putString(KEY_ACCESS_TOKEN, accessToken)
                    .putString(KEY_REFRESH_TOKEN, refreshToken)
                    .putString(KEY_EMAIL, email)
                    .putString(KEY_NAME, name)
                    .apply()

    fun getAccessToken() = getSharedPreferences(AUTH_DATA).getString(KEY_ACCESS_TOKEN, null)

    fun getRefreshToken() = getSharedPreferences(AUTH_DATA).getString(KEY_REFRESH_TOKEN, null)

    fun saveTokenPair(accessToken: String, refreshToken: String) =
            getSharedPreferences(AUTH_DATA).edit()
                    .putString(KEY_ACCESS_TOKEN, accessToken)
                    .putString(KEY_REFRESH_TOKEN, refreshToken)
                    .apply()
}