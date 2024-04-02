package com.ironsource.aura.kronos.sample.config

import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.config.type.adaptedIntConfig
import com.ironsource.aura.kronos.config.type.annotations.RemoteIntValue
import com.ironsource.aura.kronos.config.type.annotations.RemoteStringValue
import com.ironsource.aura.kronos.config.type.booleanConfig
import com.ironsource.aura.kronos.config.type.floatConfig
import com.ironsource.aura.kronos.config.type.intConfig
import com.ironsource.aura.kronos.config.type.intEnumConfig
import com.ironsource.aura.kronos.config.type.jsonConfig
import com.ironsource.aura.kronos.config.type.longConfig
import com.ironsource.aura.kronos.config.type.nullableStringConfig
import com.ironsource.aura.kronos.config.type.stringConfig
import com.ironsource.aura.kronos.config.type.stringEnumConfig
import com.ironsource.aura.kronos.source.FireBaseConfigSource
import com.ironsource.aura.kronos.source.typedSource

class SomeFeatureConfig : FeatureRemoteConfig {

	override val sourceDefinition = typedSource<FireBaseConfigSource>()

	var someInt by intConfig {
		default = 20
	}

	var someLong by longConfig {
		default = 999999999999999999
	}

	val someFloat by floatConfig {
		default = 0.5f
	}

	val someString by stringConfig {
		default = ""
	}

	val someBoolean by booleanConfig {
		default = true
	}

	val someList by jsonConfig<List<String>> {
		default = emptyList()
	}

	val someMap by jsonConfig<Map<String, List<String>>> {
		default = emptyMap()
	}

	val someNullableString by nullableStringConfig {
		default = null
	}

	val location by intEnumConfig<Location> {
		default = Location.TOP
	}

	val size by stringEnumConfig<Size> {
		default = Size.SMALL
	}

	val feature_enabled by booleanConfig {
		default = false
	}

	val someTypedInt by adaptedIntConfig {
		default = ""
		adapt {
			get { "$it" }
		}
	}

	val someLabelConfig by labelConfig {
		default = Label("Default")
	}
}

enum class Location {
	@RemoteIntValue(0)
	TOP,

	@RemoteIntValue(1)
	BOTTOM
}

enum class Size {
	@RemoteStringValue("s")
	SMALL,

	@RemoteStringValue("l")
	LARGE
}