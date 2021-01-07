package com.kdk96.projectar.auth.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestBody(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)
