package com.kdk96.projectar.auth.data.repository

import com.kdk96.projectar.auth.domain.AuthRepository
import com.kdk96.projectar.common.domain.validation.Valid
import com.kdk96.projectar.common.domain.validation.Verifiable
import com.kdk96.projectar.common.domain.validation.Violation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class AuthRepositoryImpl @Inject constructor(
//        private val authApi: AuthApi,
//        @Rx.Io private val ioScheduler: Scheduler,
//        private val authHolder: AuthHolder
) : AuthRepository {

    override  fun checkEmail(email: String): Flow<Verifiable> {
        return flow {

            delay(200)
            emit( if (Random.nextBoolean()) {
                Valid
            } else {
                Violation("shit")
            }
            )
        }
    }
//
//    override fun authorize(
//            email: String,
//            password: String
//    ) = authApi.authorize(email, password)
//            .doOnSuccess { authHolder.saveAuthData(it) }
//            .subscribeOn(ioScheduler).ignoreElement()
//
//    override fun register(
//            email: String,
//            password: String,
//            name: String
//    ) = authApi.register(email, password, name)
//            .doOnSuccess { authHolder.saveAuthData(it) }
//            .subscribeOn(ioScheduler).ignoreElement()
}