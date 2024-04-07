package com.hananrh.kronos.constraint

import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.common.initKronos
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.config.constraint.FallbackPolicy
import com.hananrh.kronos.config.constraint.maxValue
import com.hananrh.kronos.config.floatConfig
import com.hananrh.kronos.config.intConfig
import com.hananrh.kronos.config.longConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object MaxValueConstraintTest : FunSpec({

	beforeSpec {
		initKronos()
	}

	class DefaultFallbackConfig : KronosConfig by mapConfig() {

		val someInt by intConfig {
			default = 1
			maxValue = 2
			cached = false
		}
		val someLong by longConfig {
			default = 1
			maxValue = 2
			cached = false
		}
		val someFloat by floatConfig {
			default = 1f
			maxValue = 2f
			cached = false
		}
	}

	val defaultFallbackConfig = DefaultFallbackConfig()

	test("Should return default - intConfig") {
		withRemoteMap(
			"someInt" to 3,
		)
		defaultFallbackConfig.someInt shouldBeEqual 1
	}
	test("Should return default - longConfig") {
		withRemoteMap(
			"someLong" to 3L,
		)
		defaultFallbackConfig.someLong shouldBeEqual 1L
	}
	test("Should return default - floatConfig") {
		withRemoteMap(
			"someFloat" to 3f
		)
		defaultFallbackConfig.someFloat shouldBeEqual 1f
	}

	test("Should return remote value - intConfig") {
		withRemoteMap(
			"someInt" to 0,
		)
		defaultFallbackConfig.someInt shouldBeEqual 0
	}
	test("Should return remote value - longConfig") {
		withRemoteMap(
			"someLong" to 0L,
		)
		defaultFallbackConfig.someLong shouldBeEqual 0L
	}
	test("Should return remote value - floatConfig") {
		withRemoteMap(
			"someFloat" to 0f
		)
		defaultFallbackConfig.someFloat shouldBeEqual 0f
	}

	class RangeFallbackConfig : KronosConfig by mapConfig() {
		val someInt by intConfig {
			default = 1
			maxValue {
				value = 2
				fallbackPolicy = FallbackPolicy.RANGE
			}
		}
		val someLong by longConfig {
			default = 1
			maxValue {
				value = 2L
				fallbackPolicy = FallbackPolicy.RANGE
			}
		}
		val someFloat by floatConfig {
			default = 1f
			maxValue {
				value = 2f
				fallbackPolicy = FallbackPolicy.RANGE
			}
		}
	}

	val rangeFallbackConfig = RangeFallbackConfig()

	test("Should return maxValue - intConfig") {
		withRemoteMap(
			"someInt" to 3,
		)
		rangeFallbackConfig.someInt shouldBeEqual 2
	}
	test("Should return maxValue - longConfig") {
		withRemoteMap(
			"someLong" to 3L,
		)
		rangeFallbackConfig.someLong shouldBeEqual 2L
	}
	test("Should return maxValue - floatConfig") {
		withRemoteMap(
			"someFloat" to 3f
		)
		rangeFallbackConfig.someFloat shouldBeEqual 2f
	}
})