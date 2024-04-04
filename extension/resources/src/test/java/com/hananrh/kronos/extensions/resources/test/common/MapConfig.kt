package com.hananrh.kronos.extensions.resources.test.common

import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.source.typedSource

class MapConfig : FeatureRemoteConfig {

	override val sourceDefinition = typedSource<MapSource>()
}

fun mapConfig() = MapConfig()