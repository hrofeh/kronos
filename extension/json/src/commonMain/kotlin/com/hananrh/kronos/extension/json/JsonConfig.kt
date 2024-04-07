@file:Suppress("UnusedReceiverParameter")

package com.hananrh.kronos.extension.json

import com.hananrh.kronos.Kronos
import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.config.Config
import com.hananrh.kronos.config.ConfigProperty
import com.hananrh.kronos.config.ConfigSourceResolver
import kotlin.reflect.typeOf

public val kronosJsonSerializer: KronosJsonSerializer
	get() = JsonExtension.serializer

public inline fun <reified T> KronosConfig.jsonConfig(noinline block: Config<String, T>.() -> Unit): ConfigProperty<T> = com.hananrh.kronos.config.ConfigPropertyFactory.from(
	ConfigSourceResolver.String,
	validator = { it.isNotEmpty() },
	getterAdapter = {
		try {
			kronosJsonSerializer.fromJson<T>(it, typeOf<T>())
		} catch (e: Exception) {
			Kronos.logger?.e("Failed to parse json: $it", e)
			null
		}
	},
	setterAdapter = { value ->
		value?.let {
			try {
				kronosJsonSerializer.toJson(it, typeOf<T>())
			} catch (e: Exception) {
				Kronos.logger?.e("Failed to convert to json", e)
				null
			}
		}
	},
	block = block
)
