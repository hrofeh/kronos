package com.hrofeh.kronos.common

import com.hrofeh.kronos.source.ConfigSource

open class MapSource(
	private val prefix: String = "",
	private val map: MutableMap<String, Any?>
) : ConfigSource {

	override fun getInteger(key: String) = testAndGet<Int>(key)

	override fun putInteger(
		key: String,
		value: Int?
	) = putValue(key, value)

	override fun getLong(key: String) = testAndGet<Long>(key)

	override fun putLong(
		key: String,
		value: Long?
	) = putValue(key, value)

	override fun getFloat(key: String) = testAndGet<Float>(key)

	override fun putFloat(
		key: String,
		value: Float?
	) = putValue(key, value)

	override fun getBoolean(key: String) = testAndGet<Boolean>(key)

	override fun putBoolean(
		key: String,
		value: Boolean?
	) = putValue(key, value)

	override fun getString(key: String) = testAndGet<String>(key)

	override fun putString(
		key: String,
		value: String?
	) = putValue(key, value)

	override fun getStringSet(key: String) = testAndGet<Set<String>>(key)

	override fun putStringSet(
		key: String,
		value: Set<String>?
	) = putValue(key, value)

	override fun getAny(key: String) = testAndGet<Any>(key)

	override fun putAny(
		key: String,
		value: Any?
	) = putValue(key, value)

	private fun putValue(
		key: String,
		value: Any?
	) {
		map[resolveKey(key)] = value
	}

	private inline fun <reified T> testAndGet(key: String): T? {
		val value = map[resolveKey(key)]
		return if (value is T) value else null
	}

	private fun resolveKey(key: String) =
		if (prefix.isNotEmpty()) prefix + key.capitalize() else key
}

class MapSource2(map: MutableMap<String, Any?>) : MapSource(map = map)