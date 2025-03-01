package com.hrofeh.kronos.config

import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import com.hrofeh.kronos.utils.getAny
import com.hrofeh.kronos.utils.getLong
import com.hrofeh.kronos.utils.getStringSet

data class ResourcesResolver<T>(
    val resourcesGetter: Resources.(Int) -> T
) {

    companion object {
        val Int = ResourcesResolver(Resources::getInteger)
        val Long = ResourcesResolver(Resources::getLong)
        val Float = ResourcesResolver { ResourcesCompat.getFloat(this, it) }
        val Boolean = ResourcesResolver(Resources::getBoolean)
        val String = ResourcesResolver(Resources::getString)
        val StringSet = ResourcesResolver(Resources::getStringSet)
        val Any = ResourcesResolver(Resources::getAny)
    }
}