package com.kdk96.projectar.common.domain

interface ErrorMessageProvider {
    fun provide(throwable: Throwable): String
}
