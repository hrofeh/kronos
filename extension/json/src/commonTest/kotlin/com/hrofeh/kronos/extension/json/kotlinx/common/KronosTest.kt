package com.hrofeh.kronos.extension.json.kotlinx.common

import com.hrofeh.kronos.Kronos
import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.extension.json.json
import com.hrofeh.kronos.extensions.json.kotlinx.KronosKotlinxSerializer
import com.hrofeh.kronos.source.typedSource
import io.kotest.core.spec.style.FunSpec

@Suppress("UnusedReceiverParameter")
fun FunSpec.initKronos(vararg pairs: Pair<String, Any?>) {
	Kronos.init {
		logging {
			logger = ConsoleKronosLogger()
		}

		extensions {
			json {
				serializer = KronosKotlinxSerializer()
			}
		}

		configSource {
			MapSource(map = mutableMapOf(*pairs))
		}
	}
}

class MapConfig : KronosConfig {

	override val sourceDefinition = typedSource<MapSource>()
}

fun mapConfig() = MapConfig()

fun withRemoteMap(vararg pairs: Pair<String, Any?>) {
	Kronos.configSourceRepository.addSource(MapSource(map = mutableMapOf(*pairs)))
}