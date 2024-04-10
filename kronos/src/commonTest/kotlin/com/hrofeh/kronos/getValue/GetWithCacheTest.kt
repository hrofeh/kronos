package com.hrofeh.kronos.getValue

import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.common.initKronos
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.floatConfig
import com.hrofeh.kronos.config.intConfig
import com.hrofeh.kronos.config.longConfig
import com.hrofeh.kronos.config.stringConfig
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
		cacheConfig.someInt shouldBeEqual value
	}

	test("Should return original value after map is updated - longConfig") {
		val value = cacheConfig.someLong
		recalcMap()
		cacheConfig.someLong shouldBeEqual value
	}

	test("Should return original value after map is updated - floatConfig") {
		val value = cacheConfig.someFloat
		recalcMap()
		cacheConfig.someFloat shouldBeEqual value
	}

	test("Should return original value after map is updated - stringConfig") {
		val value = cacheConfig.someString
		recalcMap()
		cacheConfig.someString shouldBeEqual value
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
		nonCacheConfig.someInt shouldNotBeEqual value
	}

	test("Should return updated value after map is updated - longConfig") {
		val value = nonCacheConfig.someLong
		recalcMap()
		nonCacheConfig.someLong shouldNotBeEqual value
	}

	test("Should return updated value after map is updated - floatConfig") {
		val value = nonCacheConfig.someFloat
		recalcMap()
		nonCacheConfig.someFloat shouldNotBeEqual value
	}

	test("Should return updated value after map is updated - stringConfig") {
		val value = nonCacheConfig.someString
		recalcMap()
		nonCacheConfig.someString shouldNotBeEqual value
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
