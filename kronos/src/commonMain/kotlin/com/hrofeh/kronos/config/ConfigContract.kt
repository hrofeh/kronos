package com.hrofeh.kronos.config

import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.config.constraint.Constraint
import com.hrofeh.kronos.source.SourceDefinition
import kotlin.properties.ReadWriteProperty

public typealias SimpleConfig<T> = Config<T, T>

public interface Defaulted<Raw, Actual> {

	public var default: Actual

	public fun default(
		provider: () -> Actual
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

public interface Adaptable<Raw, Actual> {

	public fun adapt(block: Adapter<Raw, Actual>.() -> Unit)
}

public interface Adapter<Raw, Actual> {

	public fun get(block: (Raw) -> Actual?)

	public fun set(block: (Actual) -> Raw?)
}

public interface Config<Raw, Actual> :
	Defaulted<Raw, Actual>,
	Constrained<Raw, Actual>,
	Processable<Actual> {

	public var key: String
	public var sourceDefinition: SourceDefinition<out Any>
	public var cached: Boolean
}

public interface AdaptedConfig<Raw, Actual> :
	Config<Raw, Actual>,
	Adaptable<Raw, Actual> {

	public var primitiveDefault: Raw

	public fun primitiveDefault(
		provider: () -> Raw
	)
}

public interface ConfigProperty<T> : ReadWriteProperty<KronosConfig, T>