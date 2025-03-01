package com.hrofeh.kronos.extensions.core.common

import com.hrofeh.kronos.Kronos
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.source.typedSource
import org.spekframework.spek2.dsl.Root

fun kronosTest(block: Root.() -> Unit): Root.() -> Unit {
	return {
		beforeGroup {
			Kronos.init {
				context = mockContext()
				logging {
					logger = ConsoleLogger()
				}
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