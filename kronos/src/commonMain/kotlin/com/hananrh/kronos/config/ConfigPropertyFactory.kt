package com.hananrh.kronos.config

public object ConfigPropertyFactory {

	internal fun <Raw, Actual> from(
		configSourceResolver: ConfigSourceResolver<Raw>,
		validator: (Raw) -> Boolean = { true },
		block: AdaptedConfig<Raw, Actual>.() -> Unit
	): ConfigProperty<Actual> = ConfigDelegate<Raw, Actual>(configSourceResolver, validator).apply(block)

	public fun <T> fromPrimitive(
		configSourceResolver: ConfigSourceResolver<T>,
		validator: (T) -> Boolean = { true },
		block: AdaptedConfig<T, T>.() -> Unit
	): ConfigProperty<T> = ConfigDelegate(configSourceResolver, validator, { it }, { it }).apply(block)

	public fun <T> fromNullablePrimitive(
		configSourceResolver: ConfigSourceResolver<T>,
		validator: (T) -> Boolean = { true },
		block: AdaptedConfig<T, T?>.() -> Unit
	): ConfigProperty<T?> = ConfigDelegate<T, T?>(configSourceResolver, validator, { it }, { it }).apply(block)

	public fun <Raw, Actual> from(
		configSourceResolver: ConfigSourceResolver<Raw>,
		validator: (Raw) -> Boolean = { true },
		getterAdapter: (Raw) -> Actual?,
		setterAdapter: (Actual) -> Raw?,
		block: AdaptedConfig<Raw, Actual>.() -> Unit
	): ConfigProperty<Actual> = ConfigDelegate(configSourceResolver, validator, getterAdapter, setterAdapter).apply(block)
}