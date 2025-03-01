package com.hrofeh.kronos.configSourceFactory

import com.hrofeh.kronos.Kronos
import com.hrofeh.kronos.common.ConsoleLogger
import com.hrofeh.kronos.common.MapSource
import com.hrofeh.kronos.common.mockContext
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.intConfig
import com.hrofeh.kronos.source.identifiableTypedSource
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ConfigSourceFactoryTest : Spek({

    beforeGroup {
        Kronos.init {
            context = mockContext()
            logging {
                logger = ConsoleLogger()
            }
            configSourceFactory<MapSource, String>(MapSource::class) {
                MapSource(prefix = it, map = mutableMapOf("prefixSomeInt" to 1), 0)
            }
        }
    }

    describe("Using config source with prefix") {

        class Config : FeatureRemoteConfig {

            override val sourceDefinition = identifiableTypedSource<MapSource, String>("prefix")

            val someInt by intConfig {
                default = 0
            }
        }

        val config = Config()

        it("Should return configured value with prefix") {
            assertEquals(1, config.someInt)
        }
    }
})
