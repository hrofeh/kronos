package com.hananrh.kronos.getValue

import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.common.initKronos
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.config.floatConfig
import com.hananrh.kronos.config.intConfig
import com.hananrh.kronos.config.longConfig
import com.hananrh.kronos.config.stringConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import kotlin.random.Random

object GetWithCache : FunSpec({

	beforeSpec {
		initKronos()
		recalcMap()
	}

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
	}

	val cacheConfig = CacheConfig()

	test("Should return original value after map is updated - intConfig") {
		val value = cacheConfig.someInt
		recalcMap()
		value shouldBeEqual cacheConfig.someInt
	}

	test("Should return original value after map is updated - longConfig") {
		val value = cacheConfig.someLong
		recalcMap()
		value shouldBeEqual cacheConfig.someLong
	}

	test("Should return original value after map is updated - floatConfig") {
		val value = cacheConfig.someFloat
		recalcMap()
		value shouldBeEqual cacheConfig.someFloat
	}

	test("Should return original value after map is updated - stringConfig") {
		val value = cacheConfig.someString
		recalcMap()
		value shouldBeEqual cacheConfig.someString
	}
})

object GetWithoutCache : FunSpec({

	beforeSpec {
		initKronos()
		recalcMap()
	}

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
	}

	val nonCacheConfig = NoCacheConfig()

	test("Should return updated value after map is updated - intConfig") {
		val value = nonCacheConfig.someInt
		recalcMap()
		value shouldNotBeEqual nonCacheConfig.someInt
	}

	test("Should return updated value after map is updated - longConfig") {
		val value = nonCacheConfig.someLong
		recalcMap()
		value shouldNotBeEqual nonCacheConfig.someLong
	}

	test("Should return updated value after map is updated - floatConfig") {
		val value = nonCacheConfig.someFloat
		recalcMap()
		value shouldNotBeEqual nonCacheConfig.someFloat
	}

	test("Should return updated value after map is updated - stringConfig") {
		val value = nonCacheConfig.someString
		recalcMap()
		value shouldNotBeEqual nonCacheConfig.someString
	}
})

private fun recalcMap() {
	withRemoteMap(
		"someInt" to Random.nextInt(),
		"someLong" to Random.nextLong(),
		"someFloat" to Random.nextFloat(),
		"someString" to "${Random.nextLong()}",
		"someBoolean" to Random.nextBoolean(),
	)
}