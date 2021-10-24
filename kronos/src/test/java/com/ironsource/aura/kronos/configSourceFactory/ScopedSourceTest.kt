package com.ironsource.aura.kronos.configSourceFactory

import com.ironsource.aura.kronos.common.MapSource
import com.ironsource.aura.kronos.common.kronosTest
import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.config.type.intConfig
import com.ironsource.aura.kronos.source.scopedSource
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ScopedSourceTest : Spek(kronosTest {

    describe("Using config source with prefix") {

        class Config : FeatureRemoteConfig {

            override val sourceDefinition = scopedSource(
                    MapSource(map = mutableMapOf("someInt" to 1)))

            val someInt by intConfig {
                default = 0
            }
        }

        val config = Config()

        it("Should return configured value with scoped source") {
            assertEquals(1, config.someInt)
        }
    }
})
