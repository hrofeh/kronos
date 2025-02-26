package com.hananrh.kronos

import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.stringConfig
import com.hananrh.kronos.source.SourceDefinition

internal class TestConfig : FeatureRemoteConfig {

    override val sourceDefinition = SourceDefinition.Class(TestSource::class)

    val testString by stringConfig {
        default = "default"
    }
}