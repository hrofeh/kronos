package com.hrofeh.kronos.getValue

import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.common.Label
import com.hrofeh.kronos.common.initKronos
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.config.adaptedStringConfig
import com.hrofeh.kronos.config.booleanConfig
import com.hrofeh.kronos.config.floatConfig
import com.hrofeh.kronos.config.intConfig
import com.hrofeh.kronos.config.longConfig
import com.hrofeh.kronos.config.stringConfig
import com.hrofeh.kronos.config.stringSetConfig
import com.hrofeh.kronos.config.typedConfig
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
		config.someInt shouldBeEqual 1
	}

	test("Should return remote value - longConfig") {
		config.someLong shouldBeEqual 1L
	}

	test("Should return remote value - floatConfig") {
		config.someFloat shouldBeEqual 1f
	}

	test("Should return remote value - stringConfig") {
		config.someString shouldBeEqual "remote"
	}

	test("Should return remote value - stringSetConfig") {
		config.someStringSet shouldBeEqual setOf("remote")
	}

	test("Should return remote value - booleanConfig") {
		config.someBoolean shouldBeEqual true
	}

	test("Should return remote value - typedConfig") {
		config.someTyped shouldBeEqual Label()
	}

	test("Should return remote value - typedStringConfig with adapter") {
		config.someLabel shouldBeEqual Label("remote")
	}
})
