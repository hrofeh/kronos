package com.hananrh.kronos.defaultValue

import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.common.Label
import com.hananrh.kronos.common.initKronos
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.config.booleanConfig
import com.hananrh.kronos.config.floatConfig
import com.hananrh.kronos.config.intConfig
import com.hananrh.kronos.config.longConfig
import com.hananrh.kronos.config.nullableStringConfig
import com.hananrh.kronos.config.stringConfig
import com.hananrh.kronos.config.stringSetConfig
import com.hananrh.kronos.config.typedConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull

object DefaultValueTest : FunSpec({

	beforeSpec {
		initKronos()
	}

	class Config : KronosConfig by mapConfig() {
		val someInt by intConfig {
			default = 0
		}
		val someLong by longConfig {
			default = 0
		}
		val someFloat by floatConfig {
			default = 0f
		}
		val someString by stringConfig {
			default = ""
		}
		val someStringSet by stringSetConfig {
			default = setOf("")
		}
		val someNullableString by nullableStringConfig {
			default = null
		}
		val someBoolean by booleanConfig {
			default = false
		}
		val someTyped by typedConfig<Label> {
			default = Label("default")
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

	test("Should return default - stringConfig") {
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

	test("Should return default - typedConfig with invalid type configured") {
		withRemoteMap("someTyped" to "Wrong")

		config.someTyped shouldBeEqual Label("default")
	}
})
