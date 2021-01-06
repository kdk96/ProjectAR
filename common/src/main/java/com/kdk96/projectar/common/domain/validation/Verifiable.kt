package com.kdk96.projectar.common.domain.validation

sealed class Verifiable

object Unknown : Verifiable()

object Valid : Verifiable()

data class Violation(val message: String) : Verifiable()
