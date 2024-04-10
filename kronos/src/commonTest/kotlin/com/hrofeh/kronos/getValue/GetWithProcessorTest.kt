package com.hrofeh.kronos.getValue

import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.common.Label
import com.hrofeh.kronos.common.initKronos
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.config.booleanConfig
import com.hrofeh.kronos.config.floatConfig
import com.hrofeh.kronos.config.intConfig
import com.hrofeh.kronos.config.longConfig
import com.hrofeh.kronos.config.stringConfig
import com.hrofeh.kronos.config.stringSetConfig
import com.hrofeh.kronos.config.typedConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object GetWithProcessorTest : FunSpec({

	beforeSpec {
		initKronos(
			"someInt" to 1,
			"someLong" to 1L,
			"someFloat" to 1f,
			"someString" to "remote",
			"someStringSet" to setOf("remote"),
			"someBoolean" to true,
			"someTyped" to Label("remote")
		)
	}

	class Config : KronosConfig by mapConfig() {
		val someInt by intConfig {
			default = 1
			process { it + 1 }
		}
		val someLong by longConfig {
			default = 1
			process { it + 1 }
		}
		val someFloat by floatConfig {
			default = 1f
			process { it + 1 }
		}
		val someString by stringConfig {
			default = ""
			process { "remote" + 1 }
		}
		val someStringSet by stringSetConfig {
			default = setOf()
			process { it + setOf("remote2") }
		}
		val someBoolean by booleanConfig {
			default = false
			process { !it }
		}
		val someTyped by typedConfig<Label> {
			default = Label("default")
			process { Label(it.value + "Processed") }
		}
	}

	val config = Config()

	test("Should return processed remote value - intConfig") {
		config.someInt shouldBeEqual 2
	}

	test("Should return processed remote value - longConfig") {
		2L shouldBeEqual config.someLong shouldBeEqual 2L
	}

	test("Should return processed remote value - floatConfig") {
		2f shouldBeEqual config.someFloat shouldBeEqual 2f
	}

	test("Should return processed remote value - stringConfig") {
		"remote1" shouldBeEqual config.someString shouldBeEqual "remote1"
	}

	test("Should return processed remote value - stringSetConfig") {
		config.someStringSet shouldBeEqual setOf("remote", "remote2")
	}

	test("Should return processed remote value - booleanConfig") {
		config.someBoolean shouldBeEqual false
	}

	test("Should return processed remote value - typedConfig") {
		config.someTyped shouldBeEqual Label("remoteProcessed")
	}
})
