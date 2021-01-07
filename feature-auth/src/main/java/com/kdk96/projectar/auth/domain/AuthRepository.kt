package com.kdk96.projectar.auth.domain

import com.kdk96.projectar.common.domain.validation.Verifiable
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun checkEmail(email: String): Flow<Verifiable>
    suspend fun signIn(email: String, password: String)
}
