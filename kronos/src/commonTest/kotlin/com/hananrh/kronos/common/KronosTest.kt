package com.hananrh.kronos.common

import com.hananrh.kronos.Kronos
import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.source.typedSource
import io.kotest.core.spec.style.FunSpec

@Suppress("UnusedReceiverParameter")
fun FunSpec.initKronos(vararg pairs: Pair<String, Any?>) {
	Kronos.init {
		logging {
			logger = ConsoleKronosLogger()
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

fun withRemoteMap2(vararg pairs: Pair<String, Any?>) {
	Kronos.configSourceRepository.addSource(MapSource2(mutableMapOf(*pairs)))
}