package com.aura.myapplication.config

import com.aura.myapplication.R
import com.aura.myapplication.config.source.MapConfigSource
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.stringConfig
import com.hananrh.kronos.extensions.json.jsonConfig
import com.hananrh.kronos.extensions.resources.defaultRes
import com.hananrh.kronos.source.typedSource

class MainScreenConfig : FeatureRemoteConfig {

	override val sourceDefinition = typedSource<MapConfigSource>()

	val defaultedConfig by stringConfig {
		defaultRes = R.string.config_default
	}

	val texts by jsonConfig<Map<String, List<String>>> {
		default = emptyMap()
	}
}