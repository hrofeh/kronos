package com.hananrh.kronos.config.constraint

import com.hananrh.kronos.config.Config
import com.ironsource.aura.dslint.annotations.DSLMandatory
import com.ironsource.aura.dslint.annotations.DSLint

var <T> Config<T, *>.minValue: T
		where T : Number, T : Comparable<T>
	@Deprecated("", level = DeprecationLevel.ERROR)
	get() = throw UnsupportedOperationException()
	set(value) {
		minValue {
			this.value = value
			this.fallbackPolicy = FallbackPolicy.DEFAULT
		}
	}

fun <T> Config<T, *>.minValue(block: RangeConstraint<T>.() -> Unit)
		where T : Number, T : Comparable<T> {
	val rangeConstraint = RangeConstraintBuilder(block)
	rangeFallback("min value", rangeConstraint) { it >= rangeConstraint.value!! }
}

var <T> Config<T, *>.maxValue: T
		where T : Number, T : Comparable<T>
	@Deprecated("", level = DeprecationLevel.ERROR)
	get() = throw UnsupportedOperationException()
	set(value) {
		maxValue {
			this.value = value
			this.fallbackPolicy = FallbackPolicy.DEFAULT
		}
	}

fun <T> Config<T, *>.maxValue(block: RangeConstraint<T>.() -> Unit)
		where T : Number, T : Comparable<T> {
	val rangeConstraint = RangeConstraintBuilder(block)
	rangeFallback("max value", rangeConstraint) { it <= rangeConstraint.value!! }
}

private fun <T, S> Config<T, S>.rangeFallback(
	name: String,
	rangeConstraint: RangeConstraint<T>,
	allowBlock: (T) -> Boolean
)
		where T : Number, T : Comparable<T> {
	constraint(name) {
		acceptIf(allowBlock)
		if (rangeConstraint.fallbackPolicy == FallbackPolicy.RANGE) {
			fallbackToPrimitive = rangeConstraint.value!!
		}
	}
}

enum class FallbackPolicy {
	DEFAULT,
	RANGE
}

@DSLint
interface RangeConstraint<T> {

	@set:DSLMandatory
	var value: T?

	@set:DSLMandatory
	var fallbackPolicy: FallbackPolicy
}

class RangeConstraintBuilder<T> private constructor() : RangeConstraint<T> {

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