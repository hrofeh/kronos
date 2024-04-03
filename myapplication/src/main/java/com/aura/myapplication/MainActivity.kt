package com.aura.myapplication

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.hananrh.kronos.Kronos
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.extensions.json.json
import com.hananrh.kronos.extensions.json.jsonConfig
import com.hananrh.kronos.extensions.json.kotlinx.KotlinxSerializer
import com.hananrh.kronos.logging.AndroidLogger
import com.hananrh.kronos.source.ConfigSource
import com.hananrh.kronos.source.typedSource

class MapSource(vararg values: Pair<String, *>) : ConfigSource {

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

class TestConfig : FeatureRemoteConfig {

	override val sourceDefinition = typedSource<MapSource>()

	val value by jsonConfig<Map<String, List<String>>> {
		default = emptyMap()
	}
}

class MainActivity : Activity() {
	override fun onCreate(savedInstanceState: Bundle?) {

		super.onCreate(savedInstanceState)
		Kronos.init {
			context = applicationContext

			logging {
				enabled = true
				logger = AndroidLogger()
			}

			configSource {
				MapSource(
					"stamInt" to 999,
					"value" to "{\"greeting\": [\"Hello\", \"World\"]}"
				)
			}

			extensions {
				json {
					serializer = KotlinxSerializer()
				}
			}
		}

		val config = TestConfig()
		Log.d("SHIT", "value = ${config.value}")
	}
}