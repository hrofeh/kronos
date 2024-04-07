package com.hananrh.kronos.extensions.json.kotlinx

import com.hananrh.kronos.extension.json.KronosJsonSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType

public class KronosKotlinxSerializer(private val json: Json = Json.Default) : KronosJsonSerializer {

	override fun toJson(
		obj: Any,
		type: KType
	): String = json.encodeToString(serializer(type), obj)

	@Suppress("UNCHECKED_CAST")
	override fun <T> fromJson(
		jsonStr: String,
		type: KType
	): T = json.decodeFromString(serializer(type) as KSerializer<T>, jsonStr)
}