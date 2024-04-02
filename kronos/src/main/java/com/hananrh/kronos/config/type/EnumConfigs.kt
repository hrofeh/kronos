@file:Suppress("unused", "UnusedReceiverParameter")

package com.hananrh.kronos.config.type

import com.hananrh.kronos.config.Config
import com.hananrh.kronos.config.ConfigProperty
import com.hananrh.kronos.config.ConfigPropertyFactory
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.SourceTypeResolver
import com.hananrh.kronos.config.type.util.RemoteValueEnumUtils

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