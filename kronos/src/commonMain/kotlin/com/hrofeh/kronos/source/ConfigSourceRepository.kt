@file:Suppress("UnusedReceiverParameter")

package com.hrofeh.kronos.source

import com.hrofeh.kronos.Kronos
import com.hrofeh.kronos.KronosConfig
import kotlin.reflect.KClass

private val STUB_CONFIG_SOURCE: ConfigSource by lazy { StubConfigSource() }

/**
 * Responsible for managing config sources.
 */
public class ConfigSourceRepository internal constructor() {

	private val configSourcesMap = mutableMapOf<SourceDefinition<*>, ConfigSource>()
	private val configSourceFactories = mutableMapOf<SourceDefinition<*>, ConfigSourceFactory<*, *>>()

	/**
	 * Add a config source factory.
	 *
	 * @param configSourceFactory config source factory
	 */
	public fun <S : ConfigSource, T : Any> addSourceFactory(
		sourceId: SourceDefinition.Class,
		configSourceFactory: ConfigSourceFactory<S, T>
	) {
		configSourceFactories[sourceId] = configSourceFactory
	}

	/**
	 * Remove a config source factory.
	 *
	 * @param sourceId source identifier
	 */
	public fun removeSourceFactory(sourceId: SourceDefinition.Class) {
		configSourceFactories.remove(sourceId)
	}

	/**
	 * Add a config source.
	 *
	 * @param configSource config source to add
	 */
	public fun addSource(configSource: ConfigSource) {
		configSourcesMap[SourceDefinition.Class(configSource::class)] = configSource
	}

	/**
	 * Remove a config source.
	 *
	 * @param sourceDefinition source identifier
	 */
	public fun removeSource(sourceDefinition: SourceDefinition<*>) {
		configSourcesMap.remove(sourceDefinition)
	}

	@Suppress("UNCHECKED_CAST")
	public fun <T : Any> getSource(sourceDefinition: SourceDefinition<T>): ConfigSource {
		// Check scoped and return instance
		if (sourceDefinition is SourceDefinition.Scoped) {
			return sourceDefinition.configSource
		}

		// Check sources map
		configSourcesMap[sourceDefinition]?.let { return it }

		// Check if identifiable source and create instance
		if (sourceDefinition is SourceDefinition.IdentifiableClass<T>) {
			val configSourceFactory = configSourceFactories[SourceDefinition.Class(
				sourceDefinition.sourceClass
			)] as ConfigSourceFactory<*, T>?
			configSourceFactory?.let { factory ->
				Kronos.logger?.v("Found factory for id = ${sourceDefinition.id}, generating source")
				return factory.create(sourceDefinition.id).also {
					configSourcesMap[sourceDefinition] = it
				}
			}
		}

		Kronos.logger?.v("Unable to find source for id = $sourceDefinition, returning stub source")
		return STUB_CONFIG_SOURCE
	}

	public fun interface ConfigSourceFactory<S : ConfigSource, T : Any> {

		public fun create(id: T): S
	}
}

public sealed class SourceDefinition<T> {

	public data class Class(val sourceClass: KClass<out ConfigSource>) : SourceDefinition<Nothing>()
	public data class IdentifiableClass<T : Any>(
		val sourceClass: KClass<out ConfigSource>,
		val id: T
	) : SourceDefinition<T>()

	public data class Scoped(val configSource: ConfigSource) : SourceDefinition<Nothing>()
}

public fun KronosConfig.scopedSource(configSource: ConfigSource): SourceDefinition.Scoped = SourceDefinition.Scoped(
	configSource
)

public inline fun <reified T : ConfigSource> KronosConfig.typedSource(): SourceDefinition.Class = SourceDefinition.Class(
	T::class
)

public inline fun <reified T : ConfigSource, S : Any> KronosConfig.identifiableTypedSource(id: S): SourceDefinition.IdentifiableClass<S> = SourceDefinition.IdentifiableClass(
	T::class, id
)