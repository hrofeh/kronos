package com.ironsource.aura.kronos

import com.ironsource.aura.kronos.common.kronosTest
import com.ironsource.aura.kronos.common.mapConfig
import com.ironsource.aura.kronos.common.withRemoteMap
import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.config.type.intConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object KeyTest : Spek(kronosTest {

    describe("Usage of correct config key") {

        class Config : FeatureRemoteConfig by mapConfig() {
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
            withRemoteMap("someInt" to 0)
        }

        it("Should use key by property name when key not set") {
            assertEquals(0, config.someInt)
        }

        it("Should use user provided key when set") {
            assertEquals(0, config.someIntWithKey)
        }
    }
})
