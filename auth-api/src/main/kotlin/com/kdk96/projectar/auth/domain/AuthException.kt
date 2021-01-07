package com.kdk96.projectar.auth.domain

open class AuthException(cause: Throwable? = null) : RuntimeException(cause)

class InvalidUsernameException(cause: Throwable? = null) : AuthException(cause)

class NoSuchAccountException : AuthException()

class InvalidPasswordException(cause: Throwable? = null) : AuthException(cause)

class UserDisabledException(cause: Throwable? = null) : AuthException(cause)
