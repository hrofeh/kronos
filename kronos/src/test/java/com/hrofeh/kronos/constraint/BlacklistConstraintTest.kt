package com.hrofeh.kronos.constraint

import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.constraint.blacklist
import com.hrofeh.kronos.config.type.intConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object BlacklistConstraintTest : Spek(com.hrofeh.kronos.common.kronosTest {

    describe("Blacklist should control acceptable remote values") {

        class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {
            val someInt by intConfig {
                default = 2
                cached = false
                blacklist = listOf(1, 3, 5)
            }
        }

        val config = Config()

        it("Should return remote value when not in blacklist") {
            com.hrofeh.kronos.common.withRemoteMap("someInt" to 4)

            assertEquals(4, config.someInt)
        }

        it("Should return default value when remote value in blacklist") {
            com.hrofeh.kronos.common.withRemoteMap("someInt" to 3)

            assertEquals(2, config.someInt)
        }
    }
})