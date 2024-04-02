package com.hananrh.kronos.config

object ConfigPropertyFactory {

	internal fun <Raw, Actual> from(
		sourceTypeResolver: SourceTypeResolver<Raw>,
		validator: (Raw) -> Boolean = { true },
		block: AdaptedConfig<Raw, Actual>.() -> Unit
	): ConfigProperty<Actual> = ConfigDelegate<Raw, Actual>(sourceTypeResolver, validator).apply(block)

	fun <T> fromPrimitive(
		sourceTypeResolver: SourceTypeResolver<T>,
		validator: (T) -> Boolean = { true },
		block: AdaptedConfig<T, T>.() -> Unit
	): ConfigProperty<T> = ConfigDelegate(sourceTypeResolver, validator, { it }, { it }).apply(block)

	fun <T> fromNullablePrimitive(
		sourceTypeResolver: SourceTypeResolver<T>,
		validator: (T) -> Boolean = { true },
		block: AdaptedConfig<T, T?>.() -> Unit
	): ConfigProperty<T?> = ConfigDelegate<T, T?>(sourceTypeResolver, validator, { it }, { it }).apply(block)

	fun <Raw, Actual> from(
		sourceTypeResolver: SourceTypeResolver<Raw>,
		validator: (Raw) -> Boolean = { true },
		getterAdapter: (Raw) -> Actual?,
		setterAdapter: (Actual) -> Raw?,
		block: AdaptedConfig<Raw, Actual>.() -> Unit
	): ConfigProperty<Actual> = ConfigDelegate(sourceTypeResolver, validator, getterAdapter, setterAdapter).apply(block)
}