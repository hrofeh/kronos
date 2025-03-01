package com.hrofeh.kronos.configSourceFactory

import com.hrofeh.kronos.common.MapSource
import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.intConfig
import com.hrofeh.kronos.source.scopedSource
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ScopedSourceTest : Spek(com.hrofeh.kronos.common.kronosTest {

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
