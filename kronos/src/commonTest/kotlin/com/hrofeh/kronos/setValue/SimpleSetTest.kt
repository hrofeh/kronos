package com.hrofeh.kronos.setValue

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

object SimpleSetTest : FunSpec({

	beforeSpec {
		initKronos()
	}

	class Config : KronosConfig by mapConfig() {
		var someInt by intConfig { default = 1 }
		var someLong by longConfig { default = 1 }
		var someFloat by floatConfig { default = 1f }
		var someString by stringConfig { default = "" }
		var someStringSet by stringSetConfig { default = setOf() }
		var someBoolean by booleanConfig { default = false }
		var someTyped by typedConfig<Label> { default = Label("default") }
	}

	val config = Config()

	test("Should return set value - intConfig") {
		config.someInt = 1
		config.someInt shouldBeEqual 1
		config.someInt = 2
		config.someInt shouldBeEqual 2
	}

	test("Should return set value - longConfig") {
		config.someLong = 1
		config.someLong shouldBeEqual 1L
		config.someLong = 2
		config.someLong shouldBeEqual 2L
	}

	test("Should return set value - floatConfig") {
		config.someFloat = 1f
		config.someFloat shouldBeEqual 1f
		config.someFloat = 2f
		config.someFloat shouldBeEqual 2f
	}

	test("Should return set value - stringConfig") {
		config.someString = ""
		config.someString shouldBeEqual ""
		config.someString = "new"
		config.someString shouldBeEqual "new"
	}

	test("Should return set value - stringSetConfig") {
		config.someStringSet = setOf("")
		config.someStringSet shouldBeEqual setOf("")
		config.someStringSet = setOf("new")
		config.someStringSet shouldBeEqual setOf("new")
	}

	test("Should return set value - booleanConfig") {
		config.someBoolean = true
		config.someBoolean shouldBeEqual true
		config.someBoolean = false
		config.someBoolean shouldBeEqual false
	}

	test("Should return set value - anyConfig") {
		config.someTyped = Label()
		config.someTyped shouldBeEqual Label()

		config.someTyped = Label("new")
		config.someTyped shouldBeEqual Label("new")
	}
})