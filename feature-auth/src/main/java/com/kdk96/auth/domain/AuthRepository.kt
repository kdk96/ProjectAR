package com.kdk96.auth.domain

import io.reactivex.Completable

interface AuthRepository {
    fun checkEmail(email: String): Completable
    fun authorize(email: String, password: String): Completable
    fun register(email: String, password: String, name: String): Completable
}