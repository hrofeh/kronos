package com.hananrh.kronos.extensions.property.configPropertyApi

import android.graphics.Color
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.booleanConfig
import com.hananrh.kronos.config.type.colorConfig
import com.hananrh.kronos.config.type.floatConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.config.type.longConfig
import com.hananrh.kronos.config.type.nullableStringConfig
import com.hananrh.kronos.config.type.stringConfig
import com.hananrh.kronos.config.type.stringSetConfig
import com.hananrh.kronos.config.utils.ColorInt
import com.hananrh.kronos.extensions.property.common.kronosTest
import com.hananrh.kronos.extensions.property.common.mapConfig
import com.hananrh.kronos.extensions.property.getAdaptedConfigProperty
import com.hananrh.kronos.extensions.property.getConfigProperty
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ConfigPropertyApiGetDefaultValueTest : Spek(kronosTest {

	describe("Delegate getDefaultValue should return defined default") {

		class Config : FeatureRemoteConfig by mapConfig() {
			val someInt by intConfig {
				default = 0
			}
			val someLong by longConfig {
				default = 0
			}
			val someFloat by floatConfig {
				default = 0f
			}
			val someString by stringConfig {
				default = ""
			}
			val someStringSet by stringSetConfig {
				default = setOf("")
			}
			val someNullableString by nullableStringConfig {
				default = null
			}
			val someBoolean by booleanConfig {
				default = false
			}
			val someColor by colorConfig {
				default = ColorInt(
					Color.WHITE
				)
			}
		}

		val config = Config()

		it("Should return default - intConfig") {
			assertEquals(0, config.getConfigProperty(Config::someInt).defaultValue)
		}

		it("Should return default - longConfig") {
			assertEquals(0, config.getConfigProperty(Config::someLong).defaultValue)
		}

		it("Should return default - floatConfig") {
			assertEquals(0f, config.getConfigProperty(Config::someFloat).defaultValue)
		}

		it("Should return default - stringConfig") {
			assertEquals("", config.getConfigProperty(Config::someString).defaultValue)
		}

		it("Should return default - stringSetConfig") {
			assertEquals(
				setOf(""),
				config.getConfigProperty(Config::someStringSet).defaultValue
			)
		}

		it("Should return default - nullableStringConfig") {
			assertEquals(
				null,
				config.getConfigProperty(Config::someNullableString).defaultValue
			)
		}

		it("Should return default - booleanConfig") {
			assertEquals(false, config.getConfigProperty(Config::someBoolean).defaultValue)
		}

		it("Should return default - colorConfig") {
			assertEquals(
				ColorInt(
					Color.WHITE
				), config.getAdaptedConfigProperty<Config, String, ColorInt>(Config::someColor).defaultValue
			)
		}
	}
})