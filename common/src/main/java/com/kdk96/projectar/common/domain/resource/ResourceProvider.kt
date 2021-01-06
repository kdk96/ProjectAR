package com.kdk96.projectar.common.domain.resource

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes resId: Int): String
}
