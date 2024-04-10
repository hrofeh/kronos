package com.hrofeh.kronos

import com.hrofeh.kronos.source.SourceDefinition

public interface KronosConfig {

	public val sourceDefinition: SourceDefinition<out Any>
}