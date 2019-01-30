package com.kdk96.common.di

import javax.inject.Qualifier
import javax.inject.Scope

class Rx private constructor() {
    @Qualifier
    annotation class MainThread

    @Qualifier
    annotation class Io
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerFlow

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerFragment