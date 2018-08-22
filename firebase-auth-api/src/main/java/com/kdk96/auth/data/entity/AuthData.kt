package com.kdk96.auth.data.entity

data class AuthRequestBody(val email: String, val password: String, val returnSecureToken: Boolean = true)

data class AuthResponseBody(val idToken: String, val refreshToken: String, val email: String, val displayName: String)

data class RegisterResponseBody(val idToken: String, val refreshToken: String, val email: String)

data class NameUpdateRequestBody(val idToken: String, val displayName: String)

data class NameUpdateResponseBody(val displayName: String)