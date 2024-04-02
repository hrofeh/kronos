package com.hananrh.kronos.extensions.core

import com.hananrh.kronos.config.FeatureRemoteConfig
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

val FeatureRemoteConfig.all: Map<String, Any?>
	get() = mapOf(*javaClass.kotlin.memberProperties.mapNotNull { prop ->
		prop.asConfigPropertySafe(this)?.let { configProp ->
			Pair(configProp.key, prop.get(this@all))
		}
	}.toTypedArray())

fun FeatureRemoteConfig.clearCache() {
	javaClass.kotlin.memberProperties.mapNotNull {
		it.asConfigPropertySafe(this@clearCache)
	}.forEach {
		it.clearCache()
	}
}

private fun KProperty1<FeatureRemoteConfig, *>.asConfigPropertySafe(instance: FeatureRemoteConfig) =
	try {
		asConfigProperty(instance)
	} catch (e: IllegalArgumentException) {
		null
	}