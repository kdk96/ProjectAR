package com.kdk96.projectar.data

import com.kdk96.projectar.R
import com.kdk96.projectar.common.domain.ErrorMessageProvider
import com.kdk96.projectar.common.domain.resource.ResourceProvider
import io.ktor.client.features.ResponseException
import io.ktor.utils.io.errors.IOException
import javax.inject.Inject

class DefaultErrorMessageProvider @Inject constructor(
    private val resourceProvider: ResourceProvider
) : ErrorMessageProvider {

    override fun provide(throwable: Throwable): String = resourceProvider.getString(
        when (throwable) {
            is IOException -> R.string.network_error
            is ResponseException -> R.string.server_error
            else -> R.string.unknown_error
        }
    )
}
