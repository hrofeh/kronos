package com.hrofeh.kronos.converter.kotlinx_serialization

import com.hrofeh.kronos.config.type.util.JsonConverter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType

class KotlinxSerializationConverter(private val json: Json = Json.Default) : JsonConverter {

	override fun toJson(
		obj: Any,
		type: KType
	) = json.encodeToString(serializer(type), obj)

	@Suppress("UNCHECKED_CAST")
	override fun <T> fromJson(
		jsonStr: String,
		type: KType
	) = json.decodeFromString(serializer(type) as KSerializer<T>, jsonStr)
}