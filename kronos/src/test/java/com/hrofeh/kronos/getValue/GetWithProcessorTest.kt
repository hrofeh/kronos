package com.hrofeh.kronos.getValue

import com.hrofeh.kronos.common.Label
import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.reflect.KClass
import kotlin.test.assertEquals

object GetWithProcessorTest : Spek(com.hrofeh.kronos.common.kronosTest {

    describe("Config with processor should process remote value") {

        class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {
            val someInt by intConfig {
                default = 1
                process { it + 1 }
            }
            val someLong by longConfig {
                default = 1
                process { it + 1 }
            }
            val someFloat by floatConfig {
                default = 1f
                process { it + 1 }
            }
            val someString by stringConfig {
                default = ""
                process { "remote" + 1 }
            }
            val someStringSet by stringSetConfig {
                default = setOf()
                process { HashSet<String>(it).apply { add("remote2") } }
            }
            val someBoolean by booleanConfig {
                default = false
                process { !it }
            }
            val someTyped by typedConfig<Label> {
                default = Label("default")
                process { Label(it.value + "Processed") }
            }
        }

        val config = Config()

        beforeGroup {
            com.hrofeh.kronos.common.withRemoteMap(
                "someInt" to 1,
                "someLong" to 1L,
                "someFloat" to 1f,
                "someString" to "remote",
                "someStringSet" to setOf("remote"),
                "someBoolean" to true,
                "someTyped" to Label("remote")
            )
        }

        it("Should return processed remote value - intConfig") {
            assertEquals(2, config.someInt)
        }

        it("Should return processed remote value - longConfig") {
            assertEquals(2L, config.someLong)
        }

        it("Should return processed remote value - floatConfig") {
            assertEquals(2f, config.someFloat)
        }

        it("Should return processed remote value - stringConfig") {
            assertEquals("remote1", config.someString)
        }

        it("Should return processed remote value - stringSetConfig") {
            assertEquals(setOf("remote", "remote2"), config.someStringSet)
        }
        it("Should return processed remote value - booleanConfig") {
            assertEquals(false, config.someBoolean)
        }

        it("Should return processed remote value - typedConfig") {
            assertEquals(Label("remoteProcessed"), config.someTyped)
        }
    }
})
