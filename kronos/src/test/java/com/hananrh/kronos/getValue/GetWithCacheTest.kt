package com.hananrh.kronos.getValue

import com.hananrh.kronos.common.Label
import com.hananrh.kronos.common.kronosTest
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.config.type.booleanConfig
import com.hananrh.kronos.config.type.floatConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.config.type.longConfig
import com.hananrh.kronos.config.type.stringConfig
import com.hananrh.kronos.config.type.typedConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.Random
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

object GetCache : Spek(kronosTest {

	describe("Cached config should calculate value only once") {

		class CacheConfig : KronosConfig by mapConfig() {
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
			val someBoolean by booleanConfig {
				default = false
				cached = true
			}
			val someTyped by typedConfig<Label> {
				default = Label("default")
				cached = true
			}
		}

		val cacheConfig = CacheConfig()

		beforeGroup {
			recalcMap()
		}

		it("Should return original value after map is updated - intConfig") {
			val value = cacheConfig.someInt
			recalcMap()
			assertEquals(value, cacheConfig.someInt)
		}

		it("Should return original value after map is updated - longConfig") {
			val value = cacheConfig.someLong
			recalcMap()
			assertEquals(value, cacheConfig.someLong)
		}

		it("Should return original value after map is updated - floatConfig") {
			val value = cacheConfig.someFloat
			recalcMap()
			assertEquals(value, cacheConfig.someFloat)
		}

		it("Should return original value after map is updated - stringConfig") {
			val value = cacheConfig.someString
			recalcMap()
			assertEquals(value, cacheConfig.someString)
		}

		it("Should return original value after map is updated - typedConfig") {
			val value = cacheConfig.someTyped
			recalcMap()
			assertEquals(value, cacheConfig.someTyped)
		}
	}

	describe("Non cached config should resolve value on every field read") {

		class NoCacheConfig : KronosConfig by mapConfig() {
			val someInt by intConfig {
				default = 1
				cached = false
			}
			val someLong by longConfig {
				default = 1
				cached = false
			}
			val someFloat by floatConfig {
				default = 1f
				cached = false
			}
			val someString by stringConfig {
				default = ""
				cached = false
			}
			val someBoolean by booleanConfig {
				default = false
				cached = false
			}
			val someTyped by typedConfig<Label> {
				default = Label("")
				cached = false
			}
		}

		val nonCacheConfig = NoCacheConfig()

		beforeGroup {
			recalcMap()
		}

		it("Should return updated value after map is updated - intConfig") {
			val value = nonCacheConfig.someInt
			recalcMap()
			assertNotEquals(value, nonCacheConfig.someInt)
		}

		it("Should return updated value after map is updated - longConfig") {
			val value = nonCacheConfig.someLong
			recalcMap()
			assertNotEquals(value, nonCacheConfig.someLong)
		}

		it("Should return updated value after map is updated - floatConfig") {
			val value = nonCacheConfig.someFloat
			recalcMap()
			assertNotEquals(value, nonCacheConfig.someFloat)
		}

		it("Should return updated value after map is updated - stringConfig") {
			val value = nonCacheConfig.someString
			recalcMap()
			assertNotEquals(value, nonCacheConfig.someString)
		}

		it("Should return updated value after map is updated - typedConfig") {
			val value = nonCacheConfig.someTyped
			recalcMap()
			assertNotEquals(value, nonCacheConfig.someTyped)
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
		"someBoolean" to random.nextBoolean(),
		"someTyped" to Label(UUID.randomUUID().toString())
	)
}
