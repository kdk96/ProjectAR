package com.kdk96.projectar.auth.data.entity

import kotlinx.serialization.Serializable

@Serializable
internal data class FetchProvidersResponse(
    val registered: Boolean
)
