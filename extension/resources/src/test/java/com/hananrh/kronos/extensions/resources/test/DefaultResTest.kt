package com.hananrh.kronos.extensions.resources.test

import android.content.res.Resources
import com.hananrh.kronos.Kronos
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.adaptedIntConfig
import com.hananrh.kronos.config.type.booleanConfig
import com.hananrh.kronos.config.type.floatConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.config.type.longConfig
import com.hananrh.kronos.config.type.stringConfig
import com.hananrh.kronos.config.type.stringSetConfig
import com.hananrh.kronos.extensions.resources.defaultRes
import com.hananrh.kronos.extensions.resources.resources
import com.hananrh.kronos.extensions.resources.test.common.ConsoleLogger
import com.hananrh.kronos.extensions.resources.test.common.mapConfig
import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object DefaultResTest : Spek({

	beforeGroup {
		Kronos.init {
			logging {
				logger = ConsoleLogger()
			}

			extensions {
				resources {
					resources = mockk<Resources>().apply {
						every { getInteger(any()) } returns 1
						every { getFloat(any()) } returns 1f
						every { getString(any()) } returns "test"
						every { getBoolean(any()) } returns true
						every { getStringArray(any()) } returns arrayOf("test")
					}
				}
			}
		}
	}

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
			val someStringSet by stringSetConfig {
				defaultRes = 0
			}
			val someAdaptedConfig by adaptedIntConfig<String> {
				defaultRes = 0
				adapt {
					get {
						"$it"
					}
				}
			}
		}

		val config = Config()

		it("Should return default resource - intConfig") {
			assertEquals(1, config.someInt)
		}

		it("Should return default resource - longConfig") {
			assertEquals(1, config.someLong)
		}

		it("Should return default resource - floatConfig") {
			assertEquals(1f, config.someFloat)
		}

		it("Should return default resource - stringConfig") {
			assertEquals("test", config.someString)
		}

		it("Should return default resource - booleanConfig") {
			assertEquals(true, config.someBoolean)
		}

		it("Should return default resource - string set") {
			assertEquals(setOf("test"), config.someStringSet)
		}

		it("Should return default resource - adapted config") {
			assertEquals("1", config.someAdaptedConfig)
		}
	}
})
