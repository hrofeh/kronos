package com.hananrh.kronos.extensions.core.configPropertyApi

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
import com.hananrh.kronos.config.type.util.ColorInt
import com.hananrh.kronos.extensions.core.asConfigProperty
import com.hananrh.kronos.extensions.core.common.kronosTest
import com.hananrh.kronos.extensions.core.common.mapConfig
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
			assertEquals(0, Config::someInt.asConfigProperty(config).defaultValue)
		}

		it("Should return default - longConfig") {
			assertEquals(0, Config::someLong.asConfigProperty(config).defaultValue)
		}

		it("Should return default - floatConfig") {
			assertEquals(0f, Config::someFloat.asConfigProperty(config).defaultValue)
		}

		it("Should return default - stringConfig") {
			assertEquals("", Config::someString.asConfigProperty(config).defaultValue)
		}

		it("Should return default - stringSetConfig") {
			assertEquals(
				setOf(""),
				Config::someStringSet.asConfigProperty(config).defaultValue
			)
		}

		it("Should return default - nullableStringConfig") {
			assertEquals(
				null,
				Config::someNullableString.asConfigProperty(config).defaultValue
			)
		}

		it("Should return default - booleanConfig") {
			assertEquals(false, Config::someBoolean.asConfigProperty(config).defaultValue)
		}
	}
})