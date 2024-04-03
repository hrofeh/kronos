package com.aura.myapplication.config

import com.aura.myapplication.config.source.MapConfigSource
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.extensions.json.jsonConfig
import com.hananrh.kronos.source.typedSource

class MainScreenConfig : FeatureRemoteConfig {

	override val sourceDefinition = typedSource<MapConfigSource>()

	val texts by jsonConfig<Map<String, List<String>>> {
		default = emptyMap()
	}
}