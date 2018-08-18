package com.kdk96.common.di

import javax.inject.Qualifier

class Rx private constructor() {
    @Qualifier
    annotation class MainThread

    @Qualifier
    annotation class Io
}