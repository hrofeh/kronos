@file:Suppress("unused", "UnusedReceiverParameter")

package com.hananrh.kronos.config.type

import com.hananrh.kronos.config.AdaptedConfig
import com.hananrh.kronos.config.Config
import com.hananrh.kronos.config.ConfigPropertyFactory
import com.hananrh.kronos.config.ConfigSourceResolver
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.SimpleConfig

fun <T> FeatureRemoteConfig.adaptedIntConfig(block: AdaptedConfig<Int, T>.() -> Unit) =
	ConfigPropertyFactory.from(ConfigSourceResolver.Int, block = block)

fun <T> FeatureRemoteConfig.adaptedLongConfig(block: AdaptedConfig<Long, T>.() -> Unit) =
	ConfigPropertyFactory.from(ConfigSourceResolver.Long, block = block)

fun <T> FeatureRemoteConfig.adaptedFloatConfig(block: AdaptedConfig<Float, T>.() -> Unit) =
	ConfigPropertyFactory.from(ConfigSourceResolver.Float, block = block)

fun <T> FeatureRemoteConfig.adaptedStringConfig(block: AdaptedConfig<String, T>.() -> Unit) =
	ConfigPropertyFactory.from(ConfigSourceResolver.String, block = block)

fun <T> FeatureRemoteConfig.adaptedStringSetConfig(block: AdaptedConfig<Set<String>, T>.() -> Unit) =
	ConfigPropertyFactory.from(ConfigSourceResolver.StringSet, block = block)

fun <T> FeatureRemoteConfig.adaptedBooleanConfig(block: AdaptedConfig<Boolean, T>.() -> Unit) =
	ConfigPropertyFactory.from(ConfigSourceResolver.Boolean, block = block)

fun FeatureRemoteConfig.nullableStringConfig(block: Config<String, String?>.() -> Unit) =
	ConfigPropertyFactory.fromNullablePrimitive(ConfigSourceResolver.String, block = block)

fun FeatureRemoteConfig.nullableStringSetConfig(block: Config<Set<String>, Set<String>?>.() -> Unit) =
	ConfigPropertyFactory.fromNullablePrimitive(ConfigSourceResolver.StringSet, block = block)

fun FeatureRemoteConfig.intConfig(block: SimpleConfig<Int>.() -> Unit) =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.Int, block = block)

fun FeatureRemoteConfig.longConfig(block: SimpleConfig<Long>.() -> Unit) =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.Long, block = block)

fun FeatureRemoteConfig.floatConfig(block: SimpleConfig<Float>.() -> Unit) =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.Float, block = block)

fun FeatureRemoteConfig.stringConfig(block: SimpleConfig<String>.() -> Unit) =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.String, block = block)

fun FeatureRemoteConfig.stringSetConfig(block: SimpleConfig<Set<String>>.() -> Unit) =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.StringSet, block = block)

fun FeatureRemoteConfig.booleanConfig(block: SimpleConfig<Boolean>.() -> Unit) =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.Boolean, block = block)

inline fun <reified T> FeatureRemoteConfig.typedConfig(noinline block: AdaptedConfig<Any, T>.() -> Unit) =
	ConfigPropertyFactory.from(
		ConfigSourceResolver.Any,
		getterAdapter = { it as? T },
		setterAdapter = { it },
		block = block
	)