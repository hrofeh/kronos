@file:Suppress("unused", "UnusedReceiverParameter")

package com.hananrh.kronos.config.type

import android.content.res.Resources
import android.graphics.Color
import android.webkit.URLUtil
import com.hananrh.kronos.Kronos
import com.hananrh.kronos.config.Config
import com.hananrh.kronos.config.ConfigPropertyFactory
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.ResourcesResolver
import com.hananrh.kronos.config.SimpleConfig
import com.hananrh.kronos.config.SourceTypeResolver
import com.hananrh.kronos.config.type.util.ColorInt
import com.hananrh.kronos.utils.getColorHex
import kotlin.reflect.typeOf

fun FeatureRemoteConfig.urlConfig(block: SimpleConfig<String>.() -> Unit) =
	ConfigPropertyFactory.fromPrimitive(
		SourceTypeResolver.string(),
		validator = { URLUtil.isValidUrl(it) },
		block = block
	)

fun FeatureRemoteConfig.textConfig(block: SimpleConfig<String>.() -> Unit) = stringConfig(block)

inline fun <reified T> FeatureRemoteConfig.jsonConfig(noinline block: Config<String, T>.() -> Unit) = ConfigPropertyFactory.from(
	SourceTypeResolver.string(),
	validator = { it.isNotEmpty() },
	getterAdapter = {
		try {
			Kronos.jsonConverter!!.fromJson<T>(it, typeOf<T>())
		} catch (e: Exception) {
			Kronos.logger?.e("Failed to parse json: $it", e)
			null
		}
	},
	setterAdapter = { value ->
		value?.let {
			try {
				Kronos.jsonConverter!!.toJson(it, typeOf<T>())
			} catch (e: Exception) {
				Kronos.logger?.e("Failed to convert to json", e)
				null
			}
		}
	},
	block = block
)

fun FeatureRemoteConfig.colorConfig(block: Config<String, ColorInt>.() -> Unit) = ConfigPropertyFactory.from(
	SourceTypeResolver.string(
		resourcesResolver = ResourcesResolver(Resources::getColorHex)
	),
	validator = { it.isNotEmpty() },
	getterAdapter = {
		try {
			ColorInt(
				Color.parseColor(it)
			)
		} catch (e: Exception) {
			Kronos.logger?.e("Failed to parse color hex: $it", e)
			null
		}
	},
	setterAdapter = { "#" + Integer.toHexString(it.value) },
	block = block
)