package com.ironsource.aura.kronos.common

import com.google.gson.Gson
import com.ironsource.aura.kronos.Kronos
import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.converter.gson.GsonConverter
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

fun mapConfig() = object : FeatureRemoteConfig {
    override val source = MapSource::class
}

fun map2Config() = object : FeatureRemoteConfig {
    override val source = MapSource2::class
}

fun withRemoteMap(vararg pairs: Pair<String, Any?>) {
    Kronos.configSourceRepository.addSource(MapSource(mutableMapOf(*pairs)))
}

fun withRemoteMap2(vararg pairs: Pair<String, Any?>) {
    Kronos.configSourceRepository.addSource(MapSource2(mutableMapOf(*pairs)))
}