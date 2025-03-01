package com.hrofeh.kronos.constraint

import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.constraint.whitelist
import com.hrofeh.kronos.config.type.intConfig
import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object WhitelistConstraintTest : Spek(com.hrofeh.kronos.common.kronosTest {

    describe("Whitelist should control acceptable remote values") {

        class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {
            val someInt by intConfig {
                default = 1
                cached = false
                whitelist = listOf(1, 3, 5)
            }
        }

        val config = Config()

        it("Should return remote value when in whitelist") {
            com.hrofeh.kronos.common.withRemoteMap("someInt" to 5)

            assertEquals(5, config.someInt)
        }

        it("Should return default value when remote value not in whitelist") {
            com.hrofeh.kronos.common.withRemoteMap("someInt" to 2)

            assertEquals(1, config.someInt)
        }
    }
})