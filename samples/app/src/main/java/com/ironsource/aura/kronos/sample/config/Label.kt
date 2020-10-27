package com.ironsource.aura.kronos.sample.config

import com.ironsource.aura.kronos.config.Config
import com.ironsource.aura.kronos.config.ConfigPropertyFactory
import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.config.SourceTypeResolver

data class Label(val value: String)

fun FeatureRemoteConfig.labelConfig(block: Config<String, Label>.() -> Unit) =
        ConfigPropertyFactory.from(SourceTypeResolver.string(),
                validator = { it.isNotEmpty() },
                getterAdapter = { Label(it) },
                setterAdapter = { it.value },
                block = block
        )