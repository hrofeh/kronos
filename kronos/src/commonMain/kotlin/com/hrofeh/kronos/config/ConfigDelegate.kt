@file:Suppress("RedundantVisibilityModifier")

package com.hrofeh.kronos.config

import com.hrofeh.kronos.Kronos
import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.config.constraint.Constraint
import com.hrofeh.kronos.config.constraint.ConstraintBuilder
import com.hrofeh.kronos.source.ConfigSource
import com.hrofeh.kronos.source.MutableConfigSource
import com.hrofeh.kronos.source.SourceDefinition
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal class ConfigDelegate<Raw, Actual> internal constructor(
	private val sourceResolver: ConfigSourceResolver<Raw>,
	private val validator: (Raw) -> Boolean
) : ConfigProperty<Actual>, AdaptedConfig<Raw, Actual>, ConfigDelegateApi<Raw, Actual> {

	constructor(
		sourceResolver: ConfigSourceResolver<Raw>,
		validator: (Raw) -> Boolean,
		getterAdapter: (Raw) -> Actual?,
		setterAdapter: (Actual) -> Raw?
	) : this(sourceResolver, validator) {
		adapt {
			get(getterAdapter)
			set(setterAdapter)
		}
	}

	override lateinit var key: String
	override lateinit var sourceDefinition: SourceDefinition<out Any>

	private lateinit var defaultProvider: (() -> Actual)

	override var cached: Boolean = false

	private var processor: ((Actual) -> Actual)? = null
	private var adapter: AdapterBuilder<Raw, Actual>? = null

	private val constraints: MutableList<ConstraintBuilder<Raw, Actual?>> = mutableListOf()

	private var value: Actual? = null
	private var cacheSet: Boolean = false // To support null cache values

	override var default: Actual
		get() = defaultProvider()
		set(value) {
			default { value }
		}

	override var primitiveDefault: Raw
		get() = throw UnsupportedOperationException("No getter for DSL property")
		set(value) {
			primitiveDefault { value }
		}

	override fun primitiveDefault(provider: () -> Raw) {
		default {
			getterAdapter(provider()) ?: throw IllegalArgumentException("Failed to adapt primitive default")
		}
	}

	override fun default(provider: () -> Actual) {
		defaultProvider = provider
	}

	override fun constraint(
		name: String?,
		block: Constraint<Raw, Actual?>.() -> Unit
	) {
		constraints.add(ConstraintBuilder(name, { getterAdapter }, block))
	}

	override fun adapt(block: Adapter<Raw, Actual>.() -> Unit) {
		adapter = AdapterBuilder<Raw, Actual>().apply(block)
	}

	override fun process(processor: (Actual) -> Actual) {
		this.processor = processor
	}

	// TODO - solution for multi-platform synchronized here
	override fun getValue(
		thisRef: KronosConfig,
		property: KProperty<*>
	): Actual {
		val key = resolveKey(property)
		val source = resolveSource(thisRef)

		if (cacheSet) {
			Kronos.logger?.v(
				"${source::class.simpleName}: Found cached value - using value \"$key\"=$value"
			)

			@Suppress("UNCHECKED_CAST")
			return value as Actual
		}

		assertRequiredGetterValues(property)

		val value = sourceResolver.sourceGetter(source, key)
		if (value == null) {
			return defaultProvider().also {
				Kronos.logger?.v("${source::class.simpleName}: Remote value not found - using default value \"$key\"=$it")
			}
		}

		if (!validator(value)) {
			return defaultProvider().also {
				Kronos.logger?.v("${source::class.simpleName}: Remote value validation failed - using default value \"$key\"=$it")
			}
		}

		constraints.forEach { constraint ->
			val valid = constraint.verify(value)
			if (!valid) {
				val msg = "Constraint ${constraint.name} validation failed"
				val fallbackValue = constraint.fallbackProvider?.invoke(value)
				return fallbackValue?.also {
					Kronos.logger?.v("${source::class.simpleName}: $msg - using fallback value \"$key\"=$it")
				} ?: defaultProvider().also {
					Kronos.logger?.v("${source::class.simpleName}: $msg - using default value \"$key\"=$it")
				}
			}
		}

		val adaptedValue = getterAdapter(value)?.let { processor?.invoke(it) ?: it }
		if (adaptedValue == null) {
			return defaultProvider().also {
				Kronos.logger?.v("${source::class.simpleName}: Failed to adapt remote value - using default value \"$key\"=$it")
			}
		}

		Kronos.logger?.v("${source::class.simpleName}: Remote value configured - using remote value \"$key\"=$value")

		return adaptedValue.also {
			if (cached) {
				setCache(adaptedValue)
			}
		}
	}

	private fun assertRequiredGetterValues(property: KProperty<*>) {
		checkNotNull(property, defaultProvider, "get", "default value")
		checkNotNull(property, adapter?.getter, "get", "adapter")
	}

	override fun setValue(
		thisRef: KronosConfig,
		property: KProperty<*>,
		value: Actual
	) {
		assertRequiredSetterValues(property)

		val source = resolveSource(thisRef)
		val key = resolveKey(property)

		if (source !is MutableConfigSource) {
			throw UnsupportedOperationException("Cannot set config value for $key, var config properties can only be used with mutable config sources")
		}

		Kronos.logger?.v("${source::class.simpleName}: Setting value \"$key\"=$value")

		sourceResolver.sourceSetter(source, key, setterAdapter(value))
	}

	override fun getKey(property: KProperty<*>) = resolveKey(property)

	private fun assertRequiredSetterValues(property: KProperty<*>) {
		checkNotNull(property, adapter?.setter, "set", "setter adapter")
	}

	private fun setCache(
		value: Actual
	) {
		this.value = value
		this.cacheSet = true
	}

	override fun clearCache() {
		value = null
		cacheSet = false
	}

	private fun resolveKey(property: KProperty<*>) =
		if (::key.isInitialized) key else property.name

	private fun resolveSource(thisRef: KronosConfig): ConfigSource {
		val sourceId = if (::sourceDefinition.isInitialized) sourceDefinition else thisRef.sourceDefinition
		return Kronos.configSourceRepository.getSource(sourceId)
	}

	private fun checkNotNull(
		property: KProperty<*>,
		value: Any?,
		opName: String,
		name: String
	) {
		checkNotNull(value) {
			"Failed to $opName value - no $name provided for config \"${resolveKey(property)}\""
		}
	}

	override fun getRawValue(
		thisRef: KronosConfig,
		property: KProperty<*>
	) = sourceResolver.sourceGetter(resolveSource(thisRef), resolveKey(property))

	private val getterAdapter
		get() = adapter!!.getter!!

	private val setterAdapter
		get() = adapter!!.setter!!
}

private fun <T, S> ConstraintBuilder<T, S>.verify(value: T): Boolean {
	verifiers.forEach {
		if (!it(value)) {
			return false
		}
	}

	return true
}

private class AdapterBuilder<Raw, Actual> : Adapter<Raw, Actual> {
	companion object {

		operator fun <Raw, Actual> invoke(block: Adapter<Raw, Actual>.() -> Unit) =
			AdapterBuilder<Raw, Actual>().apply(block)
	}

	internal var getter: ((Raw) -> Actual?)? = null
	internal var setter: ((Actual) -> Raw?)? = null

	override fun get(block: (Raw) -> Actual?) {
		getter = block
	}

	override fun set(block: (Actual) -> Raw?) {
		setter = block
	}
}

public interface ConfigDelegateApi<Raw, Actual> : ReadOnlyProperty<KronosConfig, Actual> {
	public val default: Actual

	public fun getKey(property: KProperty<*>): String

	public fun getRawValue(
		thisRef: KronosConfig,
		property: KProperty<*>
	): Raw?

	public fun clearCache()
}