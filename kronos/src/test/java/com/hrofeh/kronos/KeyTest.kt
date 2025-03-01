package com.hrofeh.kronos

import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.intConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.reflect.KClass
import kotlin.test.assertEquals

object KeyTest : Spek(com.hrofeh.kronos.common.kronosTest {

    describe("Usage of correct config key") {

        class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {
            val someInt by intConfig {
                default = 1
            }

            val someIntWithKey by intConfig {
                key = "someInt"
                default = 1
            }
        }

        val config = Config()

        beforeGroup {
            com.hrofeh.kronos.common.withRemoteMap("someInt" to 0)
        }

        it("Should use key by property name when key not set") {
            assertEquals(0, config.someInt)
        }

        it("Should use user provided key when set") {
            assertEquals(0, config.someIntWithKey)
        }
    }
})
