package com.kdk96.auth.data.storage

import android.content.Context

class Prefs(private val context: Context) {
    companion object {
        private const val AUTH_DATA = "auth_data"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_EMAIL = "email"
        private const val KEY_NAME = "name"
        private const val KEY_PHOTO_URL = "photo url"
    }

    fun saveAuthData(accessToken: String, refreshToken: String, email: String, name: String) =
            getSharedPreferences(AUTH_DATA).edit()
                    .putString(KEY_ACCESS_TOKEN, accessToken)
                    .putString(KEY_REFRESH_TOKEN, refreshToken)
                    .putString(KEY_EMAIL, email)
                    .putString(KEY_NAME, name)
                    .commit()

    fun getAccessToken() = getSharedPreferences(AUTH_DATA).getString(KEY_ACCESS_TOKEN, null)

    fun getRefreshToken() = getSharedPreferences(AUTH_DATA).getString(KEY_REFRESH_TOKEN, null)

    fun saveTokenPair(accessToken: String, refreshToken: String) =
            getSharedPreferences(AUTH_DATA).edit()
                    .putString(KEY_ACCESS_TOKEN, accessToken)
                    .putString(KEY_REFRESH_TOKEN, refreshToken)
                    .commit()

    fun getEmail() = getSharedPreferences(AUTH_DATA).getString(KEY_EMAIL, "johndoe@gmail.com")

    fun getName() = getSharedPreferences(AUTH_DATA).getString(KEY_NAME, "John Doe")

    fun getPhotoUrl() = getSharedPreferences(AUTH_DATA).getString(KEY_PHOTO_URL, null)

    fun saveAccountData(email: String, name: String, photoUrl: String?) = getSharedPreferences(AUTH_DATA)
            .edit()
            .putString(KEY_EMAIL, email)
            .putString(KEY_NAME, name)
            .putString(KEY_PHOTO_URL, photoUrl)
            .commit()

    fun removeAccountData() = getSharedPreferences(AUTH_DATA).edit().clear().commit()

    private fun getSharedPreferences(prefsName: String) =
            context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
}