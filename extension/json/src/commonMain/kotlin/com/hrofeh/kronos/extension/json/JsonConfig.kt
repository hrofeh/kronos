@file:Suppress("UnusedReceiverParameter")

package com.hrofeh.kronos.extension.json

import com.hrofeh.kronos.Kronos
import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.config.Config
import com.hrofeh.kronos.config.ConfigProperty
import com.hrofeh.kronos.config.ConfigSourceResolver
import kotlin.reflect.typeOf

public val kronosJsonSerializer: KronosJsonSerializer
	get() = JsonExtension.serializer

public inline fun <reified T> KronosConfig.jsonConfig(noinline block: Config<String, T>.() -> Unit): ConfigProperty<T> = com.hrofeh.kronos.config.ConfigPropertyFactory.from(
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
