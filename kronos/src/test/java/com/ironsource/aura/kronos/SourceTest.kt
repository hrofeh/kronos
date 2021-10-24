package com.ironsource.aura.kronos

import com.ironsource.aura.kronos.common.*
import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.config.type.intConfig
import com.ironsource.aura.kronos.source.SourceDefinition
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object SourceTest : Spek(kronosTest {

    describe("Usage of correct config source") {

        class Config : FeatureRemoteConfig by mapConfig() {

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
            withRemoteMap("someInt" to 1)

            assertEquals(1, config.classSourceInt)
        }

        it("Should use source set in config and ignore class source") {
            withRemoteMap2("someInt" to 2)

            assertEquals(2, config.specificSourceInt)
        }
    }
})
