package com.kdk96.projectar.common.domain.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class Result<out T> {

    object Loading : Result<Nothing>()

    data class Data<T>(val value: T) : Result<T>()

    data class Error(val throwable: Throwable) : Result<Nothing>()
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> = map { data -> Result.Data(data) }
    .catch<Result<T>> { throwable -> emit(Result.Error(throwable)) }
    .onStart { emit(Result.Loading) }

typealias UnitResult = Result<Unit>

fun <T> Result<T>.asError() = this as? Result.Error
