package com.hananrh.kronos.getValue

import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.common.Label
import com.hananrh.kronos.common.initKronos
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.config.adaptedStringConfig
import com.hananrh.kronos.config.booleanConfig
import com.hananrh.kronos.config.floatConfig
import com.hananrh.kronos.config.intConfig
import com.hananrh.kronos.config.longConfig
import com.hananrh.kronos.config.stringConfig
import com.hananrh.kronos.config.stringSetConfig
import com.hananrh.kronos.config.typedConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object SimpleGetTest : FunSpec({

	beforeSpec {
		initKronos(
			"someInt" to 1,
			"someLong" to 1L,
			"someFloat" to 1f,
			"someString" to "remote",
			"someStringSet" to setOf("remote"),
			"someBoolean" to true,
			"someTyped" to Label(),
			"someLabel" to "remote",
			"someUrl" to "www.google.com"
		)
	}

	class Config : KronosConfig by mapConfig() {
		val someInt by intConfig { default = 1 }
		val someLong by longConfig { default = 1 }
		val someFloat by floatConfig { default = 1f }
		val someString by stringConfig { default = "" }
		val someStringSet by stringSetConfig { default = setOf() }
		val someBoolean by booleanConfig { default = false }
		val someTyped by typedConfig<Label> { default = Label("default") }
		var someLabel by adaptedStringConfig<Label> {
			default = Label("default")
			adapt {
				get { Label(it) }
				set { it.value }
			}
		}
	}

	val config = Config()

	test("Should return remote value - intConfig") {
		1 shouldBeEqual config.someInt
	}

	test("Should return remote value - longConfig") {
		1L shouldBeEqual config.someLong
	}

	test("Should return remote value - floatConfig") {
		1f shouldBeEqual config.someFloat
	}

	test("Should return remote value - stringConfig") {
		"remote" shouldBeEqual config.someString
	}

	test("Should return remote value - stringSetConfig") {
		setOf("remote") shouldBeEqual config.someStringSet
	}

	test("Should return remote value - booleanConfig") {
		true shouldBeEqual config.someBoolean
	}

	test("Should return remote value - typedConfig") {
		Label() shouldBeEqual config.someTyped
	}

	test("Should return remote value - typedStringConfig with adapter") {
		Label("remote") shouldBeEqual config.someLabel
	}
})
