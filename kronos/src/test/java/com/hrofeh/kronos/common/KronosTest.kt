package com.hrofeh.kronos.common

import androidx.core.content.res.ResourcesCompat
import com.hrofeh.kronos.Kronos
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.converter.kotlinx_serialization.KotlinxSerializationConverter
import com.hrofeh.kronos.source.typedSource
import io.mockk.every
import io.mockk.mockkStatic
import org.spekframework.spek2.dsl.Root

fun kronosTest(cached: Boolean = false, block: Root.() -> Unit): Root.() -> Unit {
    return {
        beforeGroup {
            mockkStatic(ResourcesCompat::class)
            every { ResourcesCompat.getFloat(any(), any()) } returns 0f

            Kronos.init {
                context = mockContext()

                defaultOptions {
                    cachedConfigs = cached
                }

                logging {
                    logger = ConsoleLogger()
                }

                jsonConverter = KotlinxSerializationConverter()
            }

            withRemoteMap()
        }

        block()
    }
}

class MapConfig : FeatureRemoteConfig {

    override val sourceDefinition = typedSource<MapSource>()
}

fun mapConfig() = MapConfig()

fun withRemoteMap(vararg pairs: Pair<String, Any?>, version: Int = 0) {
    Kronos.configSourceRepository.addSource(
        MapSource(
            map = mutableMapOf(*pairs),
            version = version
        )
    )
}

fun withRemoteMap2(vararg pairs: Pair<String, Any?>) {
    Kronos.configSourceRepository.addSource(MapSource2(mutableMapOf(*pairs)))
}