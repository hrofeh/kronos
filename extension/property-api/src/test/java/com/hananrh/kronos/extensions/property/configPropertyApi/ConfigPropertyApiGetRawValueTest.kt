package com.hananrh.kronos.extensions.property.configPropertyApi

import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.adaptedIntConfig
import com.hananrh.kronos.config.type.booleanConfig
import com.hananrh.kronos.config.type.floatConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.config.type.longConfig
import com.hananrh.kronos.config.type.stringConfig
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
			val someAdaptedInt by adaptedIntConfig<String> {
				default = ""
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
				"someAdaptedInt" to 1
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

		it("Should return raw configured value - booleanConfig") {
			assertEquals(true, config.getConfigProperty(Config::someBoolean).configuredValue)
		}

		it("Should return raw configured value - adapted config") {
			assertEquals(1, config.getAdaptedConfigProperty<Config, Int, String>(Config::someAdaptedInt).configuredValue)
		}
	}
})
