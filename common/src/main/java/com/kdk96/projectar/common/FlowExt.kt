package com.kdk96.projectar.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>?.orEmpty() = this ?: emptyFlow()

fun unitFlow(block: suspend () -> Unit): Flow<Unit> = flow {
    block.invoke()
    emit(Unit)
}
