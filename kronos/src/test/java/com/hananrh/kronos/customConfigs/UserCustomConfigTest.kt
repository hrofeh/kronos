package com.hananrh.kronos.customConfigs

import com.hananrh.kronos.common.kronosTest
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.config.Config
import com.hananrh.kronos.KronosConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

data class Label(val value: String)

@Suppress("UnusedReceiverParameter")
fun KronosConfig.labelConfig(block: Config<String, Label>.() -> Unit) =
	com.hananrh.kronos.config.ConfigPropertyFactory.from(
		com.hananrh.kronos.config.ConfigSourceResolver.String,
		validator = { it.isNotEmpty() },
		getterAdapter = { Label(it) },
		setterAdapter = { it.value },
		block = block
	)

object UserCustomConfigTest : Spek(kronosTest {

	class Config : KronosConfig by mapConfig() {

		var someLabel by labelConfig {
			default = Label("default")
			cached = false
		}
	}

	val config = Config()

	describe("Enum config field get") {

		it("Should return remote value when valid") {
			withRemoteMap(
				"someLabel" to "remote"
			)

			assertEquals(Label("remote"), config.someLabel)
		}

		it("Should return default value when invalid") {
			withRemoteMap(
				"someLabel" to ""
			)

			assertEquals(Label("default"), config.someLabel)
		}
	}

	describe("Enum config field set") {

		it("Should return set value") {
			config.someLabel = Label("override")
			assertEquals(Label("override"), config.someLabel)
		}
	}
})

