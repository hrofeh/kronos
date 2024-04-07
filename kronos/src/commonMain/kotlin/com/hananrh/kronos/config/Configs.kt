@file:Suppress("unused", "UnusedReceiverParameter")

package com.hananrh.kronos.config

import com.hananrh.kronos.KronosConfig

public fun <T> KronosConfig.adaptedIntConfig(block: AdaptedConfig<Int, T>.() -> Unit): ConfigProperty<T> =
	ConfigPropertyFactory.from(ConfigSourceResolver.Int, block = block)

public fun <T> KronosConfig.adaptedLongConfig(block: AdaptedConfig<Long, T>.() -> Unit): ConfigProperty<T> =
	ConfigPropertyFactory.from(ConfigSourceResolver.Long, block = block)

public fun <T> KronosConfig.adaptedFloatConfig(block: AdaptedConfig<Float, T>.() -> Unit): ConfigProperty<T> =
	ConfigPropertyFactory.from(ConfigSourceResolver.Float, block = block)

public fun <T> KronosConfig.adaptedStringConfig(block: AdaptedConfig<String, T>.() -> Unit): ConfigProperty<T> =
	ConfigPropertyFactory.from(ConfigSourceResolver.String, block = block)

public fun <T> KronosConfig.adaptedStringSetConfig(block: AdaptedConfig<Set<String>, T>.() -> Unit): ConfigProperty<T> =
	ConfigPropertyFactory.from(ConfigSourceResolver.StringSet, block = block)

public fun <T> KronosConfig.adaptedBooleanConfig(block: AdaptedConfig<Boolean, T>.() -> Unit): ConfigProperty<T> =
	ConfigPropertyFactory.from(ConfigSourceResolver.Boolean, block = block)

public fun KronosConfig.nullableStringConfig(block: Config<String, String?>.() -> Unit): ConfigProperty<String?> =
	ConfigPropertyFactory.fromNullablePrimitive(ConfigSourceResolver.String, block = block)

public fun KronosConfig.nullableStringSetConfig(block: Config<Set<String>, Set<String>?>.() -> Unit): ConfigProperty<Set<String>?> =
	ConfigPropertyFactory.fromNullablePrimitive(ConfigSourceResolver.StringSet, block = block)

public fun KronosConfig.intConfig(block: SimpleConfig<Int>.() -> Unit): ConfigProperty<Int> =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.Int, block = block)

public fun KronosConfig.longConfig(block: SimpleConfig<Long>.() -> Unit): ConfigProperty<Long> =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.Long, block = block)

public fun KronosConfig.floatConfig(block: SimpleConfig<Float>.() -> Unit): ConfigProperty<Float> =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.Float, block = block)

public fun KronosConfig.stringConfig(block: SimpleConfig<String>.() -> Unit): ConfigProperty<String> =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.String, block = block)

public fun KronosConfig.stringSetConfig(block: SimpleConfig<Set<String>>.() -> Unit): ConfigProperty<Set<String>> =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.StringSet, block = block)

public fun KronosConfig.booleanConfig(block: SimpleConfig<Boolean>.() -> Unit): ConfigProperty<Boolean> =
	ConfigPropertyFactory.fromPrimitive(ConfigSourceResolver.Boolean, block = block)

public inline fun <reified T> KronosConfig.typedConfig(noinline block: AdaptedConfig<Any, T>.() -> Unit): ConfigProperty<T> =
	ConfigPropertyFactory.from(
		ConfigSourceResolver.Any,
		getterAdapter = { it as? T },
		setterAdapter = { it },
		block = block
	)