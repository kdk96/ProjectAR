package com.kdk96.projectar.auth.domain

import com.kdk96.projectar.common.domain.validation.Verifiable
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun checkEmail(email: String): Flow<Verifiable>
//    fun authorize(email: String, password: String): Completable
//    fun register(email: String, password: String, name: String): Completable
}
