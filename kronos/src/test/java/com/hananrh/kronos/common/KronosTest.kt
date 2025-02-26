package com.hananrh.kronos.common

import androidx.core.content.res.ResourcesCompat
import com.hananrh.kronos.Kronos
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.converter.kotlinx_serialization.KotlinxSerializationConverter
import com.hananrh.kronos.source.typedSource
import io.mockk.every
import io.mockk.mockkStatic
import org.spekframework.spek2.dsl.Root

fun kronosTest(block: Root.() -> Unit): Root.() -> Unit {
	return {

		beforeGroup {
			mockkStatic(ResourcesCompat::class)
			every { ResourcesCompat.getFloat(any(),any()) } returns 0f

			Kronos.init {
				context = mockContext()
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

fun withRemoteMap(vararg pairs: Pair<String, Any?>) {
	Kronos.configSourceRepository.addSource(MapSource(map = mutableMapOf(*pairs)))
}

fun withRemoteMap2(vararg pairs: Pair<String, Any?>) {
	Kronos.configSourceRepository.addSource(MapSource2(mutableMapOf(*pairs)))
}