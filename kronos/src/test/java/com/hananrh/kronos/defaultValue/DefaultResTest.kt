package com.hananrh.kronos.defaultValue

import com.hananrh.kronos.common.kronosTest
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.booleanConfig
import com.hananrh.kronos.config.type.colorConfig
import com.hananrh.kronos.config.type.floatConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.config.type.longConfig
import com.hananrh.kronos.config.type.stringConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object DefaultResTest : Spek(kronosTest {

	describe("Fallback to resource default when no remote value configured") {

		class Config : FeatureRemoteConfig by mapConfig() {

			val someInt by intConfig {
				defaultRes = 0
			}
			val someLong by longConfig {
				defaultRes = 0
			}
			val someFloat by floatConfig {
				defaultRes = 0
			}
			val someString by stringConfig {
				defaultRes = 0
			}
			val someBoolean by booleanConfig {
				defaultRes = 0
			}
			val someColor by colorConfig {
				defaultRes = 0
			}
		}

		val config = Config()

		it("Should return default resource - intConfig") {
			assertEquals(0, config.someInt)
		}

		it("Should return default resource - longConfig") {
			assertEquals(0, config.someLong)
		}

		it("Should return default resource - floatConfig") {
			assertEquals(0f, config.someFloat)
		}

		it("Should return default resource - stringConfig") {
			assertEquals("", config.someString)
		}

		it("Should return default resource - booleanConfig") {
			assertEquals(false, config.someBoolean)
		}
	}
})
