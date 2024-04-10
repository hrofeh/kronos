package com.hrofeh.kronos.extension.json

import kotlin.reflect.KType

public interface KronosJsonSerializer {

	public fun toJson(
		obj: Any,
		type: KType
	): String?

	public fun <T> fromJson(
		jsonStr: String,
		type: KType
	): T?
}