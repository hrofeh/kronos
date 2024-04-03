package com.hananrh.kronos.extensions.property.configPropertyApi

import android.graphics.Color
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.*
import com.hananrh.kronos.config.utils.ColorInt
import com.hananrh.kronos.extensions.property.common.kronosTest
import com.hananrh.kronos.extensions.property.common.mapConfig
import com.hananrh.kronos.extensions.property.common.withRemoteMap
import com.hananrh.kronos.extensions.property.getAdaptedConfigProperty
import com.hananrh.kronos.extensions.property.getConfigProperty
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ConfigPropertyApiGetRawValueTest : Spek(kronosTest {

	describe("Delegate getRawValue should return raw configured value") {

		class Config : FeatureRemoteConfig by mapConfig() {

			val someInt by intConfig {
				process { it + 1 }
			}
			val someLong by longConfig {
				process { it + 1 }
			}
			val someFloat by floatConfig {
				process { it + 1 }
			}
			val someString by stringConfig {
				process { "remote" + 1 }
			}
			val someBoolean by booleanConfig {
				process { !it }
			}
			val someColor by colorConfig {
				default = ColorInt(
					Color.WHITE
				)
			}
		}

		val config = Config()

		beforeGroup {
			withRemoteMap(
				"someInt" to 1,
				"someLong" to 1L,
				"someFloat" to 1f,
				"someString" to "remote",
				"someBoolean" to true,
				"someColor" to "#000000"
			)
		}

		it("Should return raw configured value - intConfig") {
			assertEquals(1, config.getConfigProperty(Config::someInt).configuredValue)
		}

		it("Should return raw configured value - longConfig") {
			assertEquals(1L, config.getConfigProperty(Config::someLong).configuredValue)
		}

		it("Should return raw configured value - floatConfig") {
			assertEquals(1f, config.getConfigProperty(Config::someFloat).configuredValue)
		}

		it("Should return raw configured value - stringConfig") {
			assertEquals("remote", config.getConfigProperty(Config::someString).configuredValue)
		}

		it("Should return raw configured value - colorConfig") {
			assertEquals("#000000", config.getAdaptedConfigProperty<Config, String, ColorInt>(Config::someColor).configuredValue)
		}
	}
})
