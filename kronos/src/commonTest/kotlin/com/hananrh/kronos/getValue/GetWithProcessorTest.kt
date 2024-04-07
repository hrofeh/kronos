package com.hananrh.kronos.getValue

import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.common.Label
import com.hananrh.kronos.common.initKronos
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.config.booleanConfig
import com.hananrh.kronos.config.floatConfig
import com.hananrh.kronos.config.intConfig
import com.hananrh.kronos.config.longConfig
import com.hananrh.kronos.config.stringConfig
import com.hananrh.kronos.config.stringSetConfig
import com.hananrh.kronos.config.typedConfig
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
		2 shouldBeEqual config.someInt
	}

	test("Should return processed remote value - longConfig") {
		2L shouldBeEqual config.someLong
	}

	test("Should return processed remote value - floatConfig") {
		2f shouldBeEqual config.someFloat
	}

	test("Should return processed remote value - stringConfig") {
		"remote1" shouldBeEqual config.someString
	}

	test("Should return processed remote value - stringSetConfig") {
		setOf("remote", "remote2") shouldBeEqual config.someStringSet
	}

	test("Should return processed remote value - booleanConfig") {
		false shouldBeEqual config.someBoolean
	}

	test("Should return processed remote value - typedConfig") {
		Label("remoteProcessed") shouldBeEqual config.someTyped
	}
})
