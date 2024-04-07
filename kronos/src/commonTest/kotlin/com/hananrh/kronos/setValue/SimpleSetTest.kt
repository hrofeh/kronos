package com.hananrh.kronos.setValue

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
		1 shouldBeEqual config.someInt
		config.someInt = 2
		2 shouldBeEqual config.someInt
	}

	test("Should return set value - longConfig") {
		config.someLong = 1
		1L shouldBeEqual config.someLong
		config.someLong = 2
		2L shouldBeEqual config.someLong
	}

	test("Should return set value - floatConfig") {
		config.someFloat = 1f
		1f shouldBeEqual config.someFloat
		config.someFloat = 2f
		2f shouldBeEqual config.someFloat
	}

	test("Should return set value - stringConfig") {
		config.someString = ""
		"" shouldBeEqual config.someString
		config.someString = "new"
		"new" shouldBeEqual config.someString
	}

	test("Should return set value - stringSetConfig") {
		config.someStringSet = setOf("")
		setOf("") shouldBeEqual config.someStringSet
		config.someStringSet = setOf("new")
		(setOf("new") shouldBeEqual config.someStringSet)
	}

	test("Should return set value - booleanConfig") {
		config.someBoolean = true
		true shouldBeEqual config.someBoolean
		config.someBoolean = false
		false shouldBeEqual config.someBoolean
	}

	test("Should return set value - anyConfig") {
		config.someTyped = Label()
		Label() shouldBeEqual config.someTyped

		config.someTyped = Label("new")
		Label("new") shouldBeEqual config.someTyped
	}
})