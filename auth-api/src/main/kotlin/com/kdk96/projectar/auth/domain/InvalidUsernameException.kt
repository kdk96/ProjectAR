package com.kdk96.projectar.auth.domain

import java.lang.RuntimeException

class InvalidUsernameException(cause: Throwable) : RuntimeException(cause)
