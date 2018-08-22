package com.kdk96.auth.data.entity

data class AuthData(
        val accessToken: String,
        val refreshToken: String,
        val email: String,
        val name: String
)