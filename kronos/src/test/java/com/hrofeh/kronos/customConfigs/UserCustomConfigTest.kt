package com.hrofeh.kronos.customConfigs

import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.Config
import com.hrofeh.kronos.config.ConfigPropertyFactory
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.SourceTypeResolver
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

data class Label(val value: String)

@Suppress("UnusedReceiverParameter")
fun FeatureRemoteConfig.labelConfig(block: Config<String, Label>.() -> Unit) =
	ConfigPropertyFactory.from(
		SourceTypeResolver.string(),
		validator = { it.isNotEmpty() },
		getterAdapter = { Label(it) },
		setterAdapter = { it.value },
		block = block
	)

object UserCustomConfigTest : Spek(com.hrofeh.kronos.common.kronosTest {

	class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {

		var someLabel by labelConfig {
			default = Label("default")
			cached = false
		}
	}

	val config = Config()

	describe("Enum config field get") {

		it("Should return remote value when valid") {
			com.hrofeh.kronos.common.withRemoteMap(
				"someLabel" to "remote"
			)

			assertEquals(Label("remote"), config.someLabel)
		}

		it("Should return default value when invalid") {
			com.hrofeh.kronos.common.withRemoteMap(
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

