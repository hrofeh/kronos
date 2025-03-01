package com.hrofeh.kronos

import com.hrofeh.kronos.common.MapSource2
import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.common.withRemoteMap2
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.intConfig
import com.hrofeh.kronos.source.SourceDefinition
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object SourceTest : Spek(com.hrofeh.kronos.common.kronosTest {

    describe("Usage of correct config source") {

        class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {

            val classSourceInt by intConfig {
                key = "someInt"
                default = 1
            }

            val specificSourceInt by intConfig {
                key = "someInt"
                sourceDefinition = SourceDefinition.Class(MapSource2::class)
                default = 1
            }
        }

        val config = Config()

        it("Should inherit source from class when no source set for config") {
            com.hrofeh.kronos.common.withRemoteMap("someInt" to 1)

            assertEquals(1, config.classSourceInt)
        }

        it("Should use source set in config and ignore class source") {
            com.hrofeh.kronos.common.withRemoteMap2("someInt" to 2)

            assertEquals(2, config.specificSourceInt)
        }
    }
})
