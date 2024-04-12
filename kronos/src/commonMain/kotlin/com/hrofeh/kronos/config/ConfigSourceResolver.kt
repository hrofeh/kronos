package com.hrofeh.kronos.config

import com.hrofeh.kronos.source.ConfigSource
import com.hrofeh.kronos.source.MutableConfigSource


public class ConfigSourceResolver<T> private constructor(
	internal val sourceGetter: ConfigSource.(String) -> T?,
	internal val sourceSetter: MutableConfigSource.(String, T?) -> Unit
) {

	public companion object {
		public val Int: ConfigSourceResolver<Int> = ConfigSourceResolver(
			ConfigSource::getInteger,
			MutableConfigSource::putInteger
		)

		public val Long: ConfigSourceResolver<Long> = ConfigSourceResolver(
			ConfigSource::getLong,
			MutableConfigSource::putLong
		)

		public val Float: ConfigSourceResolver<Float> = ConfigSourceResolver(
			ConfigSource::getFloat,
			MutableConfigSource::putFloat
		)

		public val Boolean: ConfigSourceResolver<Boolean> = ConfigSourceResolver(
			ConfigSource::getBoolean,
			MutableConfigSource::putBoolean
		)

		public val String: ConfigSourceResolver<String> = ConfigSourceResolver(
			ConfigSource::getString,
			MutableConfigSource::putString
		)

		public val StringSet: ConfigSourceResolver<Set<String>> = ConfigSourceResolver(
			ConfigSource::getStringSet,
			MutableConfigSource::putStringSet
		)

		public val Any: ConfigSourceResolver<Any> = ConfigSourceResolver(
			ConfigSource::getAny,
			MutableConfigSource::putAny
		)
	}
}