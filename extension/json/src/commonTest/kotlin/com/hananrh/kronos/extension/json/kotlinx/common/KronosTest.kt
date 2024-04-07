package com.hananrh.kronos.extension.json.kotlinx.common

import com.hananrh.kronos.Kronos
import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.extension.json.json
import com.hananrh.kronos.extensions.json.kotlinx.KronosKotlinxSerializer
import com.hananrh.kronos.source.typedSource
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