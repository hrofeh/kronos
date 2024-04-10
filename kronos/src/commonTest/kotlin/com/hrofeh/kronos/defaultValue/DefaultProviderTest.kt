package com.hrofeh.kronos.defaultValue

import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.common.Label
import com.hrofeh.kronos.common.initKronos
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.config.booleanConfig
import com.hrofeh.kronos.config.floatConfig
import com.hrofeh.kronos.config.intConfig
import com.hrofeh.kronos.config.longConfig
import com.hrofeh.kronos.config.nullableStringConfig
import com.hrofeh.kronos.config.stringConfig
import com.hrofeh.kronos.config.stringSetConfig
import com.hrofeh.kronos.config.typedConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull

object DefaultProviderTest : FunSpec({

	beforeSpec {
		initKronos()
	}

	class Config : KronosConfig by mapConfig() {
		val someInt by intConfig {
			default { 0 }
		}

		val someLong by longConfig {
			default { 0 }
		}
		val someFloat by floatConfig {
			default { 0f }
		}
		val someString by stringConfig {
			default { "" }
		}
		val someStringSet by stringSetConfig {
			default { setOf("") }
		}
		val someNullableString by nullableStringConfig {
			default { null }
		}
		val someBoolean by booleanConfig {
			default { false }
		}
		val someTyped by typedConfig<Label> {
			default { Label("default") }
		}
	}

	val config = Config()

	test("Should return default - intConfig") {
		config.someInt shouldBeEqual 0
	}

	test("Should return default - longConfig") {
		config.someLong shouldBeEqual 0
	}

	test("Should return default - floatConfig") {
		config.someFloat shouldBeEqual 0f
	}

	test("Should return default - Should return default - stringConfig") {
		config.someString shouldBeEqual ""
	}

	test("Should return default - stringSetConfig") {
		config.someStringSet shouldBeEqual setOf("")
	}

	test("Should return default - nullableStringConfig") {
		config.someNullableString.shouldBeNull()
	}

	test("Should return default - booleanConfig") {
		config.someBoolean shouldBeEqual false
	}

	test("Should return default - typedConfig") {
		config.someTyped shouldBeEqual Label("default")
	}
})