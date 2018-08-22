package com.kdk96.auth.data.entity

import com.google.gson.annotations.SerializedName

data class RefreshBody(
        @SerializedName("id_token") val idToken: String,
        @SerializedName("refresh_token") val refreshToken: String
)