package com.hananrh.kronos.configSourceFactory

import com.hananrh.kronos.common.MapSource
import com.hananrh.kronos.common.kronosTest
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.source.scopedSource
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ScopedSourceTest : Spek(kronosTest {

    describe("Using config source with prefix") {

        class Config : FeatureRemoteConfig {

            override val sourceDefinition = scopedSource(
                MapSource(map = mutableMapOf("someInt" to 1), version = 0)
            )

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
