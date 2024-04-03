package com.hananrh.kronos.extensions.core.featureremoteconfig

import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.floatConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.config.type.longConfig
import com.hananrh.kronos.config.type.stringConfig
import com.hananrh.kronos.extensions.core.clearCache
import com.hananrh.kronos.extensions.core.common.kronosTest
import com.hananrh.kronos.extensions.core.common.mapConfig
import com.hananrh.kronos.extensions.core.common.withRemoteMap
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

object ClearCacheTest : Spek(kronosTest {

	describe("Cached config should calculate value only once") {

		class CacheConfig : FeatureRemoteConfig by mapConfig() {
			val someInt by intConfig {
				default = 1
				cached = true
			}
			val someLong by longConfig {
				default = 1
				cached = true
			}
			val someFloat by floatConfig {
				default = 1f
				cached = true
			}
			val someString by stringConfig {
				default = ""
				cached = true
			}
		}

		val cacheConfig = CacheConfig()

		beforeGroup {
			recalcMap()
		}

		it("Should return new value after map is updated and cache is cleared - intConfig") {
			val value = cacheConfig.someInt
			recalcMap()
			assertEquals(value, cacheConfig.someInt)
			cacheConfig.clearCache()
			assertNotEquals(value, cacheConfig.someInt)
		}

		it("Should return original value after map is updated and cache is cleared - longConfig") {
			val value = cacheConfig.someLong
			recalcMap()
			assertEquals(value, cacheConfig.someLong)
			cacheConfig.clearCache()
			assertNotEquals(value, cacheConfig.someLong)
		}

		it("Should return original value after map is updated and cache is cleared - floatConfig") {
			val value = cacheConfig.someFloat
			recalcMap()
			assertEquals(value, cacheConfig.someFloat)
			cacheConfig.clearCache()
			assertNotEquals(value, cacheConfig.someFloat)
		}

		it("Should return original value after map is updated and cache is cleared - stringConfig") {
			val value = cacheConfig.someString
			recalcMap()
			assertEquals(value, cacheConfig.someString)
			cacheConfig.clearCache()
			assertNotEquals(value, cacheConfig.someString)
		}
	}
})

private fun recalcMap() {
	val random = Random()

	withRemoteMap(
		"someInt" to random.nextInt(),
		"someLong" to random.nextLong(),
		"someFloat" to random.nextFloat(),
		"someString" to "${random.nextLong()}",
	)
}
