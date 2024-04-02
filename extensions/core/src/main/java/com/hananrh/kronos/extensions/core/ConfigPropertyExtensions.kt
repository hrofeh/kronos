package com.hananrh.kronos.extensions.core

import com.hananrh.kronos.config.ConfigDelegateApi
import com.hananrh.kronos.config.FeatureRemoteConfig
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.isAccessible

interface ConfigPropertyApi<Raw, Actual> {
	fun getDefaultValue(): Actual
	fun getRawValue(): Raw?
}

fun ConfigPropertyApi<*, *>.isConfigured(): Boolean = getRawValue() != null

private class ConfigPropertyApiImpl<FeatureRemoteConfigType : FeatureRemoteConfig, Raw, Actual>(
	private val instance: FeatureRemoteConfigType,
	private val property: KProperty1<FeatureRemoteConfigType, Actual>,
	private val configDelegate: ConfigDelegateApi<Raw, Actual>
) : ConfigPropertyApi<Raw, Actual> {

	override fun getDefaultValue() = configDelegate.default

	override fun getRawValue() = configDelegate.getRawValue(instance, property)
}

fun <FeatureRemoteConfigType : FeatureRemoteConfig, Actual> KProperty1<FeatureRemoteConfigType, Actual>.asConfigProperty(
	instance: FeatureRemoteConfigType
): ConfigPropertyApi<*, Actual> = ConfigPropertyApiImpl(instance, this, this.getConfigDelegate(instance))

@Suppress("UNCHECKED_CAST")
private fun <FeatureRemoteConfigType : FeatureRemoteConfig, Actual> KProperty1<FeatureRemoteConfigType, Actual>.getConfigDelegate(
	instance: FeatureRemoteConfigType
): ConfigDelegateApi<*, Actual> {
	isAccessible = true
	val delegate = getDelegate(instance)
	if (delegate !is ConfigDelegateApi<*, *>) {
		throw IllegalStateException("This function can only be called on config properties")
	}
	return delegate as ConfigDelegateApi<*, Actual>
}