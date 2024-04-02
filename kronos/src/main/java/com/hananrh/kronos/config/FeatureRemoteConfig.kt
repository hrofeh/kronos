package com.hananrh.kronos.config

import com.hananrh.kronos.source.SourceDefinition

interface FeatureRemoteConfig {

	val sourceDefinition: SourceDefinition<out Any>
}