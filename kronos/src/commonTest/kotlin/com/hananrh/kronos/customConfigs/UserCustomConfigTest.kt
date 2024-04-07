package com.hananrh.kronos.customConfigs

import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.common.initKronos
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.config.Config
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

private data class Label(val value: String)

@Suppress("UnusedReceiverParameter")
private fun KronosConfig.labelConfig(block: Config<String, Label>.() -> Unit) =
	com.hananrh.kronos.config.ConfigPropertyFactory.from(
		com.hananrh.kronos.config.ConfigSourceResolver.String,
		validator = { it.isNotEmpty() },
		getterAdapter = { Label(it) },
		setterAdapter = { it.value },
		block = block
	)

object UserCustomConfigTest : FunSpec({

	beforeSpec {
		initKronos()
	}

	class Config : KronosConfig by mapConfig() {

		var someLabel by labelConfig {
			default = Label("default")
			cached = false
		}
	}

	val config = Config()

	test("Should return remote value when valid") {
		withRemoteMap(
			"someLabel" to "remote"
		)

		config.someLabel shouldBeEqual Label("remote")
	}

	test("Should return default value when invalid") {
		withRemoteMap(
			"someLabel" to ""
		)

		config.someLabel shouldBeEqual Label("default")
	}

	test("Should return set value") {
		config.someLabel = Label("override")
		config.someLabel shouldBeEqual Label("override")
	}
})
