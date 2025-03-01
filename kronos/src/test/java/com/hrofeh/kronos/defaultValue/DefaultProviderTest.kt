package com.hrofeh.kronos.defaultValue

import android.graphics.Color
import com.hrofeh.kronos.common.Label
import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.booleanConfig
import com.hrofeh.kronos.config.type.colorConfig
import com.hrofeh.kronos.config.type.floatConfig
import com.hrofeh.kronos.config.type.intConfig
import com.hrofeh.kronos.config.type.longConfig
import com.hrofeh.kronos.config.type.nullableStringConfig
import com.hrofeh.kronos.config.type.stringConfig
import com.hrofeh.kronos.config.type.stringSetConfig
import com.hrofeh.kronos.config.type.typedConfig
import com.hrofeh.kronos.config.type.util.ColorInt
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.Random
import kotlin.test.assertEquals

object DefaultProviderTest : Spek(com.hrofeh.kronos.common.kronosTest {

    class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {
        val someInt by intConfig {
            default { 0 }
        }

        val someIntWithCache by intConfig {
            default { Random().nextInt() }
        }

        val someLong by longConfig {
            default { 0 }
        }
        val someFloat by floatConfig {
            default { 0f }
        }
        val someString by stringConfig {
            default { "" }
        }
        val someStringSet by stringSetConfig {
            default { setOf("") }
        }
        val someNullableString by nullableStringConfig {
            default { null }
        }
        val someBoolean by booleanConfig {
            default { false }
        }
        val someColor by colorConfig {
            default {
                ColorInt(
                    Color.WHITE
                )
            }
        }
        val someTyped by typedConfig<Label> {
            default { Label("default") }
        }
    }

    val config = Config()

    describe("Fallback to default provider resolved value when no remote value configured") {

        it("Should return default - intConfig") {
            assertEquals(0, config.someInt)
        }

        it("Should return default - longConfig") {
            assertEquals(0, config.someLong)
        }

        it("Should return default - floatConfig") {
            assertEquals(0f, config.someFloat)
        }

        it("Should return default - Should return default - stringConfig") {
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
            assertEquals(
                ColorInt(
                    Color.WHITE
                ), config.someColor
            )
        }

        it("Should return default - typedConfig") {
            assertEquals(Label("default"), config.someTyped)
        }
    }
})