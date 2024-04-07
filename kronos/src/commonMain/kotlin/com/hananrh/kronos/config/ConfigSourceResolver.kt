package com.hananrh.kronos.config

import com.hananrh.kronos.source.ConfigSource


public class ConfigSourceResolver<T> private constructor(
	internal val sourceGetter: ConfigSource.(String) -> T?,
	internal val sourceSetter: ConfigSource.(String, T?) -> Unit
) {

	public companion object {
		public val Int: ConfigSourceResolver<Int> = ConfigSourceResolver(
			ConfigSource::getInteger,
			ConfigSource::putInteger
		)

		public val Long: ConfigSourceResolver<Long> = ConfigSourceResolver(
			ConfigSource::getLong,
			ConfigSource::putLong
		)

		public val Float: ConfigSourceResolver<Float> = ConfigSourceResolver(
			ConfigSource::getFloat,
			ConfigSource::putFloat
		)

		public val Boolean: ConfigSourceResolver<Boolean> = ConfigSourceResolver(
			ConfigSource::getBoolean,
			ConfigSource::putBoolean
		)

		public val String: ConfigSourceResolver<String> = ConfigSourceResolver(
			ConfigSource::getString,
			ConfigSource::putString
		)

		public val StringSet: ConfigSourceResolver<Set<String>> = ConfigSourceResolver(
			ConfigSource::getStringSet,
			ConfigSource::putStringSet
		)

		public val Any: ConfigSourceResolver<Any> = ConfigSourceResolver(
			ConfigSource::getAny,
			ConfigSource::putAny
		)
	}
}