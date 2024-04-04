package com.hananrh.kronos.extensions.json

import kotlin.reflect.KType

interface JsonSerializer {

	fun toJson(
		obj: Any,
		type: KType
	): String?

	fun <T> fromJson(
		jsonStr: String,
		type: KType
	): T?
}