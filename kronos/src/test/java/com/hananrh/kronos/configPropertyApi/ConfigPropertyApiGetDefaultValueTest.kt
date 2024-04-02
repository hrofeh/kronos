//package com.hananrh.kronos.configPropertyApi
//
//import android.graphics.Color
//import com.hananrh.kronos.common.kronos
//import com.hananrh.kronos.common.mapConfig
//import com.hananrh.kronos.config.FeatureRemoteConfig
//import com.hananrh.kronos.config.asConfigProperty
//import com.hananrh.kronos.config.type.*
//import com.hananrh.kronos.config.type.util.ColorInt
//import org.spekframework.spek2.Spek
//import org.spekframework.spek2.style.specification.describe
//import kotlin.test.assertEquals
//
//object ConfigPropertyApiGetDefaultValueTest : Spek(kronosTest {
//
//    describe("Delegate getDefaultValue should return defined default") {
//
//        class Config : FeatureRemoteConfig by mapConfig() {
//            val someInt by intConfig {
//                default = 0
//            }
//            val someLong by longConfig {
//                default = 0
//            }
//            val someFloat by floatConfig {
//                default = 0f
//            }
//            val someString by stringConfig {
//                default = ""
//            }
//            val someStringSet by stringSetConfig {
//                default = setOf("")
//            }
//            val someNullableString by nullableStringConfig {
//                default = null
//            }
//            val someBoolean by booleanConfig {
//                default = false
//            }
//            val someColor by colorConfig {
//                default = ColorInt(
//                        Color.WHITE)
//            }
//        }
//
//        val config = Config()
//
//        it("Should return default - intConfig") {
//            assertEquals(0, Config::someInt.asConfigProperty(config).getDefaultValue())
//        }
//
//        it("Should return default - longConfig") {
//            assertEquals(0, Config::someLong.asConfigProperty(config).getDefaultValue())
//        }
//
//        it("Should return default - floatConfig") {
//            assertEquals(0f, Config::someFloat.asConfigProperty(config).getDefaultValue())
//        }
//
//        it("Should return default - stringConfig") {
//            assertEquals("", Config::someString.asConfigProperty(config).getDefaultValue())
//        }
//
//        it("Should return default - stringSetConfig") {
//            assertEquals(setOf(""),
//                    Config::someStringSet.asConfigProperty(config).getDefaultValue())
//        }
//
//        it("Should return default - nullableStringConfig") {
//            assertEquals(null,
//                    Config::someNullableString.asConfigProperty(config).getDefaultValue())
//        }
//
//        it("Should return default - booleanConfig") {
//            assertEquals(false, Config::someBoolean.asConfigProperty(config).getDefaultValue())
//        }
//
//        it("Should return default - colorConfig") {
//            assertEquals(ColorInt(
//                    Color.WHITE), Config::someColor.asConfigProperty(config).getDefaultValue())
//        }
//    }
//})
