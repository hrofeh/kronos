package com.ironsource.aura.kronos.config

import com.ironsource.aura.kronos.source.ConfigSource
import kotlin.reflect.KClass

interface FeatureRemoteConfig {
    val source: KClass<out ConfigSource>
}