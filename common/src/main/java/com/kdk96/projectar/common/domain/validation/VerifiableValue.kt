package com.kdk96.projectar.common.domain.validation

data class VerifiableValue<V>(
    val value: V,
    val verifiable: Verifiable
)

val VerifiableValue<*>.isValid: Boolean
    get() = verifiable == Valid
