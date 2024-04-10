package com.hrofeh.kronos.extension.json.kotlinx

import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.extension.json.jsonConfig
import com.hrofeh.kronos.extension.json.kotlinx.common.Label
import com.hrofeh.kronos.extension.json.kotlinx.common.initKronos
import com.hrofeh.kronos.extension.json.kotlinx.common.mapConfig
import com.hrofeh.kronos.extension.json.kotlinx.common.withRemoteMap
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object JsonConfigTest : FunSpec({

	beforeSpec {
		initKronos()
	}

	class Config : KronosConfig by mapConfig() {

		var someJsonList by jsonConfig<List<Label>> {
			cached = false
			default = listOf(Label("default"))
		}

		var someJson by jsonConfig<Label> {
			cached = false
			default = Label("default")
		}

		val someJsonMap by jsonConfig<Map<String, Label>> {
			cached = false
			default = mapOf("default" to Label("default"))
		}

	}

	val config = Config()

	test("Should return remote json value when valid") {
		withRemoteMap(
			"someJson" to "{\"val\":\"remote\"}",
			"someJsonList" to "[{\"val\":\"remote\"}, {\"val\":\"remote2\"}]",
			"someJsonMap" to "{\"remote\": {\"val\":\"remote\"}}"
		)

		config.someJson shouldBeEqual Label("remote")
		config.someJsonList shouldBeEqual listOf(Label("remote"), Label("remote2"))
		config.someJsonMap shouldBeEqual mapOf("remote" to Label("remote"))
	}

	test("Should return default value whe json value invalid") {
		withRemoteMap(
			"someJson" to "stam"
		)

		config.someJsonList shouldBeEqual listOf(Label("default"))

		withRemoteMap(
			"someJson" to 5
		)

		config.someJsonList shouldBeEqual listOf(Label("default"))
	}


	test("Should return set json value") {
		config.someJsonList = listOf(Label("set"))
		config.someJsonList shouldBeEqual listOf(Label("set"))
	}
})
