package com.hananrh.kronos.extensions.property

import com.hananrh.kronos.config.FeatureRemoteConfig
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

val FeatureRemoteConfig.all: Map<String, Any?>
	get() = mapOf(*javaClass.kotlin.memberProperties.mapNotNull { prop ->
		getConfigPropertySafe(prop)?.let { configProp ->
			Pair(configProp.key, prop.get(this@all))
		}
	}.toTypedArray())

fun FeatureRemoteConfig.clearCache() {
	javaClass.kotlin.memberProperties.mapNotNull {
		getConfigPropertySafe(it)
	}.forEach {
		it.clearCache()
	}
}

private fun <FR : FeatureRemoteConfig> FR.getConfigPropertySafe(property: KProperty1<FR, *>) =
	try {
		getConfigProperty(property)
	} catch (e: IllegalArgumentException) {
		null
	}