package com.kdk96.projectar.auth.domain

import com.kdk96.projectar.common.domain.validation.Violation

class InvalidCredentialsException(
    cause: Throwable,
    val emailViolation: Violation? = null,
    val passwordViolation: Violation? = null
) : RuntimeException(cause)
