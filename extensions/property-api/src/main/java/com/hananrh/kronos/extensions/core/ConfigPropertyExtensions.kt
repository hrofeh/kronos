package com.hananrh.kronos.extensions.core

import com.hananrh.kronos.config.ConfigDelegateApi
import com.hananrh.kronos.config.FeatureRemoteConfig
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.isAccessible

fun <FR : FeatureRemoteConfig, T> FR.getConfigProperty(property: KProperty1<FR, T>): ConfigPropertyApi<T, T> = getAdaptedConfigProperty(property)

fun <FR : FeatureRemoteConfig, Raw, Actual> FR.getAdaptedConfigProperty(property: KProperty1<FR, Actual>): ConfigPropertyApi<Raw, Actual> {
	property.isAccessible = true
	val delegate = property.getDelegate(this)
	if (delegate !is ConfigDelegateApi<*, *>) {
		throw IllegalArgumentException("This function can only be called on config properties")
	}
	@Suppress("UNCHECKED_CAST")
	return ConfigPropertyApiImpl(this, property, delegate as ConfigDelegateApi<Raw, Actual>)
}

interface ConfigPropertyApi<Raw, Actual> {
	val key: String
	val value: Actual
	val defaultValue: Actual
	val configuredValue: Raw?

	fun clearCache()
}

fun ConfigPropertyApi<*, *>.isConfigured(): Boolean = configuredValue != null

private class ConfigPropertyApiImpl<FR : FeatureRemoteConfig, Raw, Actual>(
	private val instance: FR,
	private val property: KProperty1<FR, Actual>,
	private val configDelegate: ConfigDelegateApi<Raw, Actual>
) : ConfigPropertyApi<Raw, Actual> {

	override val key: String
		get() = configDelegate.getKey(property)

	override val value: Actual
		get() = configDelegate.getValue(instance, property)

	override val defaultValue: Actual
		get() = configDelegate.default

	override val configuredValue: Raw?
		get() = configDelegate.getRawValue(instance, property)

	override fun clearCache() {
		configDelegate.clearCache()
	}
}