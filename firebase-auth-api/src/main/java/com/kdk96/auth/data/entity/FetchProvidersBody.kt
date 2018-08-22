package com.kdk96.auth.data.entity

import com.google.gson.annotations.SerializedName

data class FetchProvidersRequestBody(
        @SerializedName("identifier") val email: String,
        val continueUri: String = "http://localhost"
)

data class FetchProvidersResponseBody(val registered: Boolean)