package com.aura.myapplication.config.source

import com.hananrh.kronos.source.ConfigSource

class MapConfigSource(vararg values: Pair<String, *>) : ConfigSource {

	private val map = mapOf(*values)

	override fun getAny(key: String) = map[key]

	override fun getBoolean(key: String) = map[key] as? Boolean?

	override fun getFloat(key: String) = map[key] as? Float?

	override fun getInteger(key: String) = map[key] as? Int?

	override fun getLong(key: String) = map[key] as? Long?

	override fun getString(key: String) = map[key] as? String?

	override fun getStringSet(key: String) = map[key] as? Set<String>?

	override fun putAny(key: String, value: Any?) {
	}

	override fun putBoolean(key: String, value: Boolean?) {
	}

	override fun putFloat(key: String, value: Float?) {
	}

	override fun putInteger(key: String, value: Int?) {
	}

	override fun putLong(key: String, value: Long?) {
	}

	override fun putString(key: String, value: String?) {
	}

	override fun putStringSet(key: String, value: Set<String>?) {
	}
}