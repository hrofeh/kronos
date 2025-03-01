package com.hananrh.kronos.config

import com.hananrh.kronos.source.ConfigSource
import com.hananrh.kronos.source.MutableConfigSource

class ConfigSourceResolver<T> private constructor(
    val sourceGetter: ConfigSource.(String) -> T?,
    val sourceSetter: MutableConfigSource.(String, T?) -> Unit
) {

    companion object {
        val Int = ConfigSourceResolver(
            ConfigSource::getInteger,
            MutableConfigSource::putInteger
        )

        val Long = ConfigSourceResolver(
            ConfigSource::getLong,
            MutableConfigSource::putLong
        )

        val Float = ConfigSourceResolver(
            ConfigSource::getFloat,
            MutableConfigSource::putFloat
        )

        val Boolean = ConfigSourceResolver(
            ConfigSource::getBoolean,
            MutableConfigSource::putBoolean
        )

        val String = ConfigSourceResolver(
            ConfigSource::getString,
            MutableConfigSource::putString
        )

        val StringSet = ConfigSourceResolver(
            ConfigSource::getStringSet,
            MutableConfigSource::putStringSet
        )

        val Any = ConfigSourceResolver(
            ConfigSource::getAny,
            MutableConfigSource::putAny
        )
    }
}