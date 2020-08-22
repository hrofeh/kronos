@file:Suppress("unused")

package com.ironsource.aura.airconkt.config.type

import com.ironsource.aura.airconkt.config.*

fun <T> FeatureRemoteConfig.adaptedIntConfig(block: AdaptedConfig<Int, T>.() -> Unit) =
        ConfigPropertyFactory.from(SourceTypeResolver.int(), block = block)

fun <T> FeatureRemoteConfig.adaptedLongConfig(block: AdaptedConfig<Long, T>.() -> Unit) =
        ConfigPropertyFactory.from(SourceTypeResolver.long(), block = block)

fun <T> FeatureRemoteConfig.adaptedFloatConfig(block: AdaptedConfig<Float, T>.() -> Unit) =
        ConfigPropertyFactory.from(SourceTypeResolver.float(), block = block)

fun <T> FeatureRemoteConfig.adaptedStringConfig(block: AdaptedConfig<String, T>.() -> Unit) =
        ConfigPropertyFactory.from(SourceTypeResolver.string(), block = block)

fun <T> FeatureRemoteConfig.adaptedStringSetConfig(block: AdaptedConfig<Set<String>, T>.() -> Unit) =
        ConfigPropertyFactory.from(SourceTypeResolver.stringSet(), block = block)

fun <T> FeatureRemoteConfig.adaptedBooleanConfig(block: AdaptedConfig<Boolean, T>.() -> Unit) =
        ConfigPropertyFactory.from(SourceTypeResolver.boolean(), block = block)

fun FeatureRemoteConfig.nullableStringConfig(block: Config<String, String?>.() -> Unit) =
        ConfigPropertyFactory.fromNullablePrimitive(SourceTypeResolver.string(), block = block)

fun FeatureRemoteConfig.nullableStringSetConfig(block: Config<Set<String>, Set<String>?>.() -> Unit) =
        ConfigPropertyFactory.fromNullablePrimitive(SourceTypeResolver.stringSet(), block = block)

fun FeatureRemoteConfig.intConfig(block: SimpleConfig<Int>.() -> Unit) =
        ConfigPropertyFactory.fromPrimitive(SourceTypeResolver.int(), block = block)

fun FeatureRemoteConfig.longConfig(block: SimpleConfig<Long>.() -> Unit) =
        ConfigPropertyFactory.fromPrimitive(SourceTypeResolver.long(), block = block)

fun FeatureRemoteConfig.floatConfig(block: SimpleConfig<Float>.() -> Unit) =
        ConfigPropertyFactory.fromPrimitive(SourceTypeResolver.float(), block = block)

fun FeatureRemoteConfig.stringConfig(block: SimpleConfig<String>.() -> Unit) =
        ConfigPropertyFactory.fromPrimitive(SourceTypeResolver.string(), block = block)

fun FeatureRemoteConfig.stringSetConfig(block: SimpleConfig<Set<String>>.() -> Unit) =
        ConfigPropertyFactory.fromPrimitive(SourceTypeResolver.stringSet(), block = block)

fun FeatureRemoteConfig.booleanConfig(block: SimpleConfig<Boolean>.() -> Unit) =
        ConfigPropertyFactory.fromPrimitive(SourceTypeResolver.boolean(), block = block)

inline fun <reified T> FeatureRemoteConfig.typedConfig(noinline block: AdaptedConfig<Any, T>.() -> Unit) =
        ConfigPropertyFactory.from<Any, T>(SourceTypeResolver.any(),
                getterAdapter = { it as? T },
                setterAdapter = { it },
                block = block)
