package com.ironsource.aura.kronos.source

import com.ironsource.aura.kronos.Kronos
import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import kotlin.reflect.KClass

private val STUB_CONFIG_SOURCE: ConfigSource by lazy { StubConfigSource() }

/**
 * Responsible for managing config sources.
 */
class ConfigSourceRepository internal constructor() {

    private val configSourcesMap = mutableMapOf<SourceDefinition<*>, ConfigSource>()
    private val configSourceFactories = mutableMapOf<SourceDefinition<*>, ConfigSourceFactory<*, *>>()

    /**
     * Add a config source factory.
     *
     * @param configSourceFactory config source factory
     */
    @Synchronized
    fun <S : ConfigSource, T : Any> addSourceFactory(
            sourceId: SourceDefinition.Class,
            configSourceFactory: ConfigSourceFactory<S, T>) {
        configSourceFactories[sourceId] = configSourceFactory
    }

    /**
     * Remove a config source factory.
     *
     * @param sourceId source identifier
     */
    @Synchronized
    fun removeSourceFactory(sourceId: SourceDefinition.Class) {
        configSourceFactories.remove(sourceId)
    }

    /**
     * Add a config source.
     *
     * @param configSource config source to add
     */
    @Synchronized
    fun addSource(configSource: ConfigSource) {
        configSourcesMap[SourceDefinition.Class(configSource::class)] = configSource
    }

    /**
     * Remove a config source.
     *
     * @param sourceDefinition source identifier
     */
    @Synchronized
    fun removeSource(sourceDefinition: SourceDefinition<*>) {
        configSourcesMap.remove(sourceDefinition)
    }

    @Suppress("UNCHECKED_CAST")
    @Synchronized
    fun <T : Any> getSource(sourceDefinition: SourceDefinition<T>): ConfigSource {
        configSourcesMap[sourceDefinition]?.let { return it }

        if (sourceDefinition is SourceDefinition.IdentifiableClass<T>) {
            val configSourceFactory = configSourceFactories[SourceDefinition.Class(
                    sourceDefinition.sourceClass)] as ConfigSourceFactory<*, T>?
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

    fun interface ConfigSourceFactory<S : ConfigSource, T : Any> {

        fun create(id: T): S
    }
}

sealed class SourceDefinition<T> {

    data class Class(val sourceClass: KClass<out ConfigSource>) : SourceDefinition<Nothing>()
    data class IdentifiableClass<T : Any>(val sourceClass: KClass<out ConfigSource>,
                                          val id: T) : SourceDefinition<T>()
}

inline fun <reified T : ConfigSource> FeatureRemoteConfig.typedSource() = SourceDefinition.Class(
        T::class)

inline fun <reified T : ConfigSource, S : Any> FeatureRemoteConfig.identifiableTypedSource(id: S) = SourceDefinition.IdentifiableClass(
        T::class, id)