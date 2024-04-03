@file:Suppress("UnusedReceiverParameter")

package com.hananrh.kronos.extensions.json

import com.hananrh.kronos.Kronos
import com.hananrh.kronos.config.Config
import com.hananrh.kronos.config.ConfigPropertyFactory
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.SourceTypeResolver
import kotlin.reflect.KType
import kotlin.reflect.typeOf

val kronosJsonSerializer
	get() = JsonExtension.serializer

inline fun <reified T> FeatureRemoteConfig.jsonConfig(noinline block: Config<String, T>.() -> Unit) = ConfigPropertyFactory.from(
	SourceTypeResolver.string(),
	validator = { it.isNotEmpty() },
	getterAdapter = JsonGetterAdapter(typeOf<T>()),
	setterAdapter = JsonSetterAdapter(typeOf<T>()),
	block = block
)

class JsonGetterAdapter<T>(private val type: KType) : (String) -> T? {

	override fun invoke(json: String) = try {
		kronosJsonSerializer.fromJson<T>(json, type)
	} catch (e: Exception) {
		Kronos.logger?.e("Failed to parse json: $json", e)
		null
	}
}

class JsonSetterAdapter<T>(private val type: KType) : (T) -> String? {

	override fun invoke(value: T) = value?.let {
		try {
			kronosJsonSerializer.toJson(it, type)
		} catch (e: Exception) {
			Kronos.logger?.e("Failed to convert to json", e)
			null
		}
	}
}
