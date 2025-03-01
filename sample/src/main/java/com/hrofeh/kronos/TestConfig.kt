package com.hrofeh.kronos

import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.stringConfig
import com.hrofeh.kronos.source.SourceDefinition

internal class TestConfig : FeatureRemoteConfig {

    override val sourceDefinition = SourceDefinition.Class(TestSource::class)

    val testString by stringConfig {
        default = "default"
    }
}