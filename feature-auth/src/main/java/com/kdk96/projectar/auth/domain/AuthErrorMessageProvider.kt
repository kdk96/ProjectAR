package com.kdk96.projectar.auth.domain

import com.kdk96.projectar.auth.screen.R
import com.kdk96.projectar.common.domain.resource.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthErrorMessageProvider @Inject constructor(
    private val resourceProvider: ResourceProvider
) {

    val emptyEmail: String get() = resourceProvider.getString(R.string.empty_email_error)

    val invalidEmail: String get() = resourceProvider.getString(R.string.invalid_email_error)

    val noSuchAccount: String get() = resourceProvider.getString(R.string.no_such_account_error)
}
