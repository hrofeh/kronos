package com.ironsource.aura.kronos.common

import com.google.gson.Gson
import com.ironsource.aura.kronos.Kronos
import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.converter.gson.GsonConverter
import com.ironsource.aura.kronos.source.typedSource
import org.spekframework.spek2.dsl.Root

fun kronosTest(block: Root.() -> Unit): Root.() -> Unit {
    return {

        beforeGroup {
            Kronos.init {
                context = mockContext()
                logging {
                    logger = ConsoleLogger()
                }
                jsonConverter = GsonConverter(Gson())
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

fun withRemoteMap(vararg pairs: Pair<String, Any?>) {
    Kronos.configSourceRepository.addSource(MapSource(map = mutableMapOf(*pairs)))
}

fun withRemoteMap2(vararg pairs: Pair<String, Any?>) {
    Kronos.configSourceRepository.addSource(MapSource2(mutableMapOf(*pairs)))
}