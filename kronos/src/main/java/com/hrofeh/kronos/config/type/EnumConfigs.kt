@file:Suppress("unused", "UnusedReceiverParameter")

package com.hrofeh.kronos.config.type

import com.hrofeh.kronos.config.Config
import com.hrofeh.kronos.config.ConfigProperty
import com.hrofeh.kronos.config.ConfigPropertyFactory
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.SourceTypeResolver
import com.hrofeh.kronos.config.type.util.RemoteValueEnumUtils

inline fun <reified T : Enum<T>> FeatureRemoteConfig.intEnumConfig(noinline block: Config<Int, T> .() -> Unit): ConfigProperty<T> {
    return ConfigPropertyFactory.from(SourceTypeResolver.int(),
            getterAdapter = { RemoteValueEnumUtils.getIntEnumConst(T::class, it) },
            setterAdapter = {
                val remoteValue = RemoteValueEnumUtils.getIntEnumRemoteValue(T::class, it)
                remoteValue
                        ?: throw IllegalArgumentException(
                                "No remote value annotation defined for ${T::class}.$it")
            },
            block = block
    )
}

inline fun <reified T : Enum<T>> FeatureRemoteConfig.stringEnumConfig(noinline block: Config<String, T> .() -> Unit): ConfigProperty<T> {
    return ConfigPropertyFactory.from(
        SourceTypeResolver.string(),
            getterAdapter = { RemoteValueEnumUtils.getStringEnumConst(T::class, it) },
            setterAdapter = {
                val remoteValue = RemoteValueEnumUtils.getStringEnumRemoteValue(T::class, it)
                remoteValue
                        ?: throw IllegalArgumentException(
                                "No remote value annotation defined for ${T::class}.$it")
            },
            block = block
    )
}