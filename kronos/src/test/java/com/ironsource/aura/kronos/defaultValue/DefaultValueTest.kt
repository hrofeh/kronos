package com.ironsource.aura.kronos.defaultValue

import android.graphics.Color
import com.ironsource.aura.kronos.common.Label
import com.ironsource.aura.kronos.common.kronosTest
import com.ironsource.aura.kronos.common.mapConfig
import com.ironsource.aura.kronos.common.withRemoteMap
import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.config.type.*
import com.ironsource.aura.kronos.config.type.util.ColorInt
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object DefaultValueTest : Spek(kronosTest {

    describe("Fallback to default when no remote value configured") {

        class Config : FeatureRemoteConfig by mapConfig() {
            val someInt by intConfig {
                default = 0
            }
            val someLong by longConfig {
                default = 0
            }
            val someFloat by floatConfig {
                default = 0f
            }
            val someString by stringConfig {
                default = ""
            }
            val someStringSet by stringSetConfig {
                default = setOf("")
            }
            val someNullableString by nullableStringConfig {
                default = null
            }
            val someBoolean by booleanConfig {
                default = false
            }
            val someColor by colorConfig {
                default = ColorInt(
                        Color.WHITE)
            }
            val someTyped by typedConfig<Label> {
                default = Label("default")
            }
        }

        val config = Config()

        it("Should return default - intConfig") {
            assertEquals(0, config.someInt)
        }

        it("Should return default - longConfig") {
            assertEquals(0, config.someLong)
        }

        it("Should return default - floatConfig") {
            assertEquals(0f, config.someFloat)
        }

        it("Should return default - stringConfig") {
            assertEquals("", config.someString)
        }

        it("Should return default - stringSetConfig") {
            assertEquals(setOf(""), config.someStringSet)
        }

        it("Should return default - nullableStringConfig") {
            assertEquals(null, config.someNullableString)
        }

        it("Should return default - booleanConfig") {
            assertEquals(false, config.someBoolean)
        }

        it("Should return default - colorConfig") {
            assertEquals(ColorInt(
                    Color.WHITE), config.someColor)
        }

        it("Should return default - typedConfig") {
            assertEquals(Label("default"), config.someTyped)
        }

        it("Should return default - typedConfig with invalid type configured") {
            withRemoteMap("someTyped" to "Wrong")

            assertEquals(Label("default"), config.someTyped)
        }
    }
})
