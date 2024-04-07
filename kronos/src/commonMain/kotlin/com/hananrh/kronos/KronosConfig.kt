package com.hananrh.kronos

import com.hananrh.kronos.source.SourceDefinition

public interface KronosConfig {

	public val sourceDefinition: SourceDefinition<out Any>
}