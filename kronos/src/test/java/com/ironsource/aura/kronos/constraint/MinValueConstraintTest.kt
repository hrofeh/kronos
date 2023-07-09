package com.ironsource.aura.kronos.constraint

import com.ironsource.aura.kronos.common.kronosTest
import com.ironsource.aura.kronos.common.mapConfig
import com.ironsource.aura.kronos.common.withRemoteMap
import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.config.constraint.FallbackPolicy
import com.ironsource.aura.kronos.config.constraint.minValue
import com.ironsource.aura.kronos.config.type.adaptedLongConfig
import com.ironsource.aura.kronos.config.type.floatConfig
import com.ironsource.aura.kronos.config.type.intConfig
import com.ironsource.aura.kronos.config.type.longConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object MinValueConstraintTest : Spek(kronosTest {

	class DefaultFallbackConfig : FeatureRemoteConfig by mapConfig() {

		val someInt by intConfig {
			default = 3
			minValue = 2
		}
		val someLong by longConfig {
			default = 3
			minValue = 2
		}
		val someFloat by floatConfig {
			default = 3f
			minValue = 2f
		}
		val someAdapted by adaptedLongConfig {
			default = ""
			minValue = 0
			adapt {
				get { it.toString() }
			}
		}
	}

	val defaultFallbackConfig = DefaultFallbackConfig()

	describe("Remote value smaller than minValue should fallback to default") {
		beforeGroup {
			withRemoteMap(
				"someInt" to 1,
				"someLong" to 1L,
				"someFloat" to 1f,
				"someAdapted" to -1L
			)
		}

		it("Should return default - intConfig") {
			assertEquals(3, defaultFallbackConfig.someInt)
		}
		it("Should return default - longConfig") {
			assertEquals(3L, defaultFallbackConfig.someLong)
		}
		it("Should return default - floatConfig") {
			assertEquals(3f, defaultFallbackConfig.someFloat)
		}
        it("Should return default - adaptedConfig") {
            assertEquals("", defaultFallbackConfig.someAdapted)
        }
	}

	describe("Remote value equal or greater than minValue should be used") {
		beforeGroup {
			withRemoteMap(
				"someInt" to 5,
				"someLong" to 5L,
				"someFloat" to 5f
			)
		}

		it("Should return remote value - intConfig") {
			assertEquals(5, defaultFallbackConfig.someInt)
		}
		it("Should return remote value - longConfig") {
			assertEquals(5L, defaultFallbackConfig.someLong)
		}
		it("Should return remote value - floatConfig") {
			assertEquals(5f, defaultFallbackConfig.someFloat)
		}
	}

	describe("Remote value smaller than minValue with range fallback should fall to it") {
		class RangeFallbackConfig : FeatureRemoteConfig by mapConfig() {
			val someInt by intConfig {
				default = 3
				minValue {
					value = 2
					fallbackPolicy = FallbackPolicy.RANGE
				}
			}
			val someLong by longConfig {
				default = 3
				minValue {
					value = 2L
					fallbackPolicy = FallbackPolicy.RANGE
				}
			}
			val someFloat by floatConfig {
				default = 3f
				minValue {
					value = 2f
					fallbackPolicy = FallbackPolicy.RANGE
				}
			}
		}

		val rangeFallbackConfig = RangeFallbackConfig()

		beforeGroup {
			withRemoteMap(
				"someInt" to 1,
				"someLong" to 1L,
				"someFloat" to 1f
			)
		}

		it("Should return minValue - intConfig") {
			assertEquals(2, rangeFallbackConfig.someInt)
		}
		it("Should return minValue - longConfig") {
			assertEquals(2L, rangeFallbackConfig.someLong)
		}
		it("Should return minValue - floatConfig") {
			assertEquals(2f, rangeFallbackConfig.someFloat)
		}
	}
})