package com.hananrh.kronos.config.constraint

import com.hananrh.kronos.config.Config

public var <T> Config<T, *>.minValue: T
		where T : Number, T : Comparable<T>
	@Deprecated("", level = DeprecationLevel.ERROR)
	get() = throw UnsupportedOperationException()
	set(value) {
		minValue {
			this.value = value
			this.fallbackPolicy = FallbackPolicy.DEFAULT
		}
	}

public fun <T> Config<T, *>.minValue(block: RangeConstraint<T>.() -> Unit)
		where T : Number, T : Comparable<T> {
	val rangeConstraint = RangeConstraintBuilder(block)
	rangeFallback("min value", rangeConstraint) { it >= rangeConstraint.value!! }
}

public var <T> Config<T, *>.maxValue: T
		where T : Number, T : Comparable<T>
	@Deprecated("", level = DeprecationLevel.ERROR)
	get() = throw UnsupportedOperationException()
	set(value) {
		maxValue {
			this.value = value
			this.fallbackPolicy = FallbackPolicy.DEFAULT
		}
	}

public fun <T> Config<T, *>.maxValue(block: RangeConstraint<T>.() -> Unit)
		where T : Number, T : Comparable<T> {
	val rangeConstraint = RangeConstraintBuilder(block)
	rangeFallback("max value", rangeConstraint) { it <= rangeConstraint.value!! }
}

private fun <T, S> Config<T, S>.rangeFallback(
	name: String,
	rangeConstraint: RangeConstraint<T>,
	allowBlock: (T) -> Boolean
) where T : Number, T : Comparable<T> {
	constraint(name) {
		allowIf(allowBlock)
		if (rangeConstraint.fallbackPolicy == FallbackPolicy.RANGE) {
			fallbackToPrimitive = rangeConstraint.value!!
		}
	}
}

public enum class FallbackPolicy {
	DEFAULT,
	RANGE
}

//@DSLint
public interface RangeConstraint<T> {

	//	@set:DSLMandatory
	public var value: T?

	//	@set:DSLMandatory
	public var fallbackPolicy: FallbackPolicy
}

internal class RangeConstraintBuilder<T> private constructor() : RangeConstraint<T> {

	override var value: T? = null
	override lateinit var fallbackPolicy: FallbackPolicy

	companion object {

		internal operator fun <T> invoke(
			block: RangeConstraintBuilder<T>.() -> Unit
		) = RangeConstraintBuilder<T>().apply(
			block
		)
	}
}