package com.hrofeh.kronos.config

import com.hrofeh.kronos.source.SourceDefinition

interface FeatureRemoteConfig {

	val sourceDefinition: SourceDefinition<out Any>
}