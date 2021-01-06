package com.kdk96.projectar.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

fun <T> Flow<T>?.orEmpty() = this ?: emptyFlow()
