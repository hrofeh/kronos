package com.hananrh.kronos.extensions.property.featureremoteconfig

import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.booleanConfig
import com.hananrh.kronos.config.type.floatConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.config.type.longConfig
import com.hananrh.kronos.config.type.nullableStringConfig
import com.hananrh.kronos.config.type.stringConfig
import com.hananrh.kronos.config.type.stringSetConfig
import com.hananrh.kronos.extensions.property.all
import com.hananrh.kronos.extensions.property.common.kronosTest
import com.hananrh.kronos.extensions.property.common.mapConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object FeatureRemoteConfigGetAllTest : Spek(kronosTest {

	describe("All extension property should return all values") {

		class Config : FeatureRemoteConfig by mapConfig() {
			val someInt by intConfig {
				default = 1
			}
			val someLong by longConfig {
				default = 214124214124
			}
			val someFloat by floatConfig {
				default = 1.1f
			}
			val someString by stringConfig {
				default = "test"
			}
			val someStringSet by stringSetConfig {
				default = setOf("test")
			}
			val someNullableString by nullableStringConfig {
				default = null
			}
			val someBoolean by booleanConfig {
				default = true
			}
		}

		val config = Config()

		it("Should return all values") {
			val all = config.all

			assertEquals(1, all["someInt"])
			assertEquals(214124214124, all["someLong"])
			assertEquals(1.1f, all["someFloat"])
			assertEquals("test", all["someString"])
			assertEquals(setOf("test"), all["someStringSet"])
			assertEquals(1, all["someInt"])
			assertEquals(null, all["someNullableString"])
			assertEquals(true, all["someBoolean"])
		}
	}
})