package com.hananrh.kronos.config

import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.config.constraint.Constraint
import com.hananrh.kronos.source.SourceDefinition
import kotlin.properties.ReadWriteProperty

public typealias SimpleConfig<T> = Config<T, T>

//@DSLint
public interface Defaulted<Raw, Actual> {

	//	@set:DSLMandatory(group = "default")
	public var default: Actual

	//	@set:DSLMandatory(group = "default")
	public var primitiveDefault: Raw

	//	@DSLMandatory(group = "default")
	public fun default(
		provider: () -> Actual
	)

	//	@DSLMandatory(group = "default")
	public fun primitiveDefault(
		provider: () -> Raw
	)
}

public interface Constrained<Test, Fallback> {

	public fun constraint(
		name: String? = null,
		block: Constraint<Test, Fallback?>.() -> Unit
	)
}

public interface Processable<T> {

	public fun process(processor: (T) -> T)
}

//@DSLint
public interface Adaptable<Raw, Actual> {

	//	@DSLMandatory
	public fun adapt(block: Adapter<Raw, Actual>.() -> Unit)
}

//@DSLint
public interface Adapter<Raw, Actual> {

	//	@DSLMandatory
	public fun get(block: (Raw) -> Actual?)

	public fun set(block: (Actual) -> Raw?)
}

//@DSLint
public interface Config<Raw, Actual> :
	Defaulted<Raw, Actual>,
	Constrained<Raw, Actual>,
	Processable<Actual> {

	public var key: String
	public var sourceDefinition: SourceDefinition<out Any>
	public var cached: Boolean
}

//@DSLint
public interface AdaptedConfig<Raw, Actual> :
	Config<Raw, Actual>,
	Adaptable<Raw, Actual>

public interface ConfigProperty<T> : ReadWriteProperty<KronosConfig, T>