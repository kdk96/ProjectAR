package com.kdk96.projectar.data.resource

import android.content.Context
import com.kdk96.projectar.common.domain.resource.ResourceProvider
import javax.inject.Inject

class AndroidResourceProvider @Inject constructor(
    private val context: Context
) : ResourceProvider {

    override fun getString(resId: Int): String = context.getString(resId)
}
