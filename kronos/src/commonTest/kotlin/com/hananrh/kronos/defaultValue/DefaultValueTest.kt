//package com.hananrh.kronos.defaultValue
//
//import com.hananrh.kronos.KronosConfig
//import com.hananrh.kronos.common.Label
//import com.hananrh.kronos.common.kronosTest
//import com.hananrh.kronos.common.mapConfig
//import com.hananrh.kronos.common.withRemoteMap
//import com.hananrh.kronos.config.booleanConfig
//import com.hananrh.kronos.config.floatConfig
//import com.hananrh.kronos.config.intConfig
//import com.hananrh.kronos.config.longConfig
//import com.hananrh.kronos.config.nullableStringConfig
//import com.hananrh.kronos.config.stringConfig
//import com.hananrh.kronos.config.stringSetConfig
//import com.hananrh.kronos.config.typedConfig
//import org.spekframework.spek2.Spek
//import org.spekframework.spek2.style.specification.describe
//import kotlin.test.assertEquals
//
//object DefaultValueTest : Spek(kronosTest {
//
//	describe("Fallback to default when no remote value configured") {
//
//		class Config : KronosConfig by mapConfig() {
//			val someInt by intConfig {
//				default = 0
//			}
//			val someLong by longConfig {
//				default = 0
//			}
//			val someFloat by floatConfig {
//				default = 0f
//			}
//			val someString by stringConfig {
//				default = ""
//			}
//			val someStringSet by stringSetConfig {
//				default = setOf("")
//			}
//			val someNullableString by nullableStringConfig {
//				default = null
//			}
//			val someBoolean by booleanConfig {
//				default = false
//			}
//			val someTyped by typedConfig<Label> {
//				default = Label("default")
//			}
//		}
//
//		val config = Config()
//
//		it("Should return default - intConfig") {
//			assertEquals(0, config.someInt)
//		}
//
//		it("Should return default - longConfig") {
//			assertEquals(0, config.someLong)
//		}
//
//		it("Should return default - floatConfig") {
//			assertEquals(0f, config.someFloat)
//		}
//
//		it("Should return default - stringConfig") {
//			assertEquals("", config.someString)
//		}
//
//		it("Should return default - stringSetConfig") {
//			assertEquals(setOf(""), config.someStringSet)
//		}
//
//		it("Should return default - nullableStringConfig") {
//			assertEquals(null, config.someNullableString)
//		}
//
//		it("Should return default - booleanConfig") {
//			assertEquals(false, config.someBoolean)
//		}
//
//		it("Should return default - typedConfig") {
//			assertEquals(Label("default"), config.someTyped)
//		}
//
//		it("Should return default - typedConfig with invalid type configured") {
//			withRemoteMap("someTyped" to "Wrong")
//
//			assertEquals(Label("default"), config.someTyped)
//		}
//	}
//})
