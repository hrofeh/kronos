package com.hananrh.kronos.extensions.core

import com.hananrh.kronos.config.FeatureRemoteConfig
import kotlin.reflect.full.memberProperties

val FeatureRemoteConfig.all: Map<String, Any?>
	get() = mapOf(*javaClass.kotlin.memberProperties.mapNotNull {
		try {
			Pair(it.asConfigProperty(this@all).key, it.get(this@all))
		} catch (e: IllegalArgumentException) {
			null
		}
	}.toTypedArray())