package com.ironsource.aura.kronos.config

import com.ironsource.aura.kronos.source.SourceDefinition

interface FeatureRemoteConfig {

    val sourceDefinition: SourceDefinition<out Any>
}