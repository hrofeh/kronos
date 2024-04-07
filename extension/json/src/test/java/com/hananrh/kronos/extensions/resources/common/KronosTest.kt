package com.hananrh.kronos.extensions.resources.common

import com.hananrh.kronos.Kronos
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.extensions.json.json
import com.hananrh.kronos.extensions.resources.kotlinx.KotlinxSerializer
import com.hananrh.kronos.source.typedSource
import org.spekframework.spek2.dsl.Root

fun kronosTest(block: Root.() -> Unit): Root.() -> Unit {
	return {
		beforeGroup {
			Kronos.init {
				logging {
					logger = ConsoleKronosLogger()
				}
				extensions {
					json {
						serializer = KotlinxSerializer()
					}
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