package com.ironsource.aura.kronos.config.type.util

import kotlin.reflect.KType

interface JsonConverter {

	fun toJson(
		obj: Any,
		type: KType
	): String?

	fun <T> fromJson(
		jsonStr: String,
		type: KType
	): T?
}