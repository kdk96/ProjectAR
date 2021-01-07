package com.kdk96.projectar.auth.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FetchProvidersRequestBody(
    @SerialName("identifier") val email: String,
    val continueUri: String = "http://localhost"
)
