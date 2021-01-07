package com.kdk96.projectar.auth.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("idToken") val accessToken: String,
    val refreshToken: String
)

fun SignInResponse.toTokenPair() = TokenPair(
    accessToken = accessToken,
    refreshToken = refreshToken
)
