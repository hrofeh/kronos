package com.hananrh

import com.google.gson.Gson
import com.hananrh.kronos.config.type.util.JsonConverter
import kotlin.reflect.KType
import kotlin.reflect.javaType

class GsonConverter(private val gson: Gson = Gson()) : JsonConverter {

	override fun toJson(
		obj: Any,
		type: KType
	): String? = gson.toJson(obj)

	@OptIn(ExperimentalStdlibApi::class)
	override fun <T> fromJson(
		jsonStr: String,
		type: KType
	): T? = gson.fromJson<T>(jsonStr, type.javaType)
}