package com.kdk96.auth.data.repository

import io.reactivex.Completable

interface AuthApi {
    fun checkEmail(email: String): Completable
    fun authorize(email: String, password: String): Completable
    fun register(email: String, password: String, name: String): Completable
}