package com.hananrh.kronos.config

import com.hananrh.dslint.annotations.DSLMandatory
import com.hananrh.dslint.annotations.DSLint
import com.hananrh.kronos.config.constraint.Constraint
import com.hananrh.kronos.source.SourceDefinition
import kotlin.properties.ReadWriteProperty

typealias SimpleConfig<T> = Config<T, T>

@DSLint
interface Defaulted<T> {
    /**
     * Default value for config if no value found in source
     */
    @set:DSLMandatory(group = "default")
    var default: T

    /**
     * Default value for config if no value found in source, used for runtime calculated values
     */
    @DSLMandatory(group = "default")
    fun default(
        provider: () -> T
    )

    /**
     * Default resource value for config if no value found in source
     */
    @set:DSLMandatory(group = "default")
    var defaultRes: Int
}

interface Constrained<Test, Fallback> {

    /**
     * Constraint for remote config value.
     * @param name - optional name for constraint, used for logging
     * @param block - constraint builder
     */
    fun constraint(
        name: String? = null,
        block: Constraint<Test, Fallback?>.() -> Unit
    )
}

interface Processable<T> {

    /**
     * Process remote value before returning it.
     */
    fun process(processor: (T) -> T)
}

@DSLint
interface Adaptable<Raw, Actual> {

    /**
     * Adapt config to different type before returning it.
     */
    @DSLMandatory
    fun adapt(block: Adapter<Raw, Actual>.() -> Unit)
}

@DSLint
interface Adapter<Raw, Actual> {

    /**
     * Getter adapter.
     */
    @DSLMandatory
    fun get(block: (Raw) -> Actual?)

    /**
     * Setter adapter, used when config is defined as var property.
     */
    fun set(block: (Actual) -> Raw?)
}

@DSLint
interface Config<Raw, Actual> :
    Defaulted<Actual>,
    Constrained<Raw, Actual>,
    Processable<Actual> {

    /**
     * Config key in source.
     * By default, property name is used as key.
     */
    var key: String

    /**
     * Source definition for config.
     * This value will override source definition from containing [FeatureRemoteConfig].
     */
    var sourceDefinition: SourceDefinition<out Any>

    /**
     * Whether to cache config value.
     * If true, value will be cached and returned from cache until cache is invalidated.
     * Default value for all configs can be defined when initializing the SDK.
     */
    var cached: Boolean
}

@DSLint
interface AdaptedConfig<Raw, Actual> :
    Config<Raw, Actual>,
    Adaptable<Raw, Actual>

interface ConfigProperty<T> : ReadWriteProperty<FeatureRemoteConfig, T>