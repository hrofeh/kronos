package com.hrofeh.kronos.config.constraint

import com.hananrh.dslint.annotations.DSLMandatory
import com.hananrh.dslint.annotations.DSLint

@DSLint
interface Constraint<Test, Fallback> {

	var fallbackToPrimitive: Test
	var fallbackTo: Fallback

	@DSLMandatory(
		group = "constraint",
		message = "At least one constraint of type 'acceptIf' or 'denyIf' must be defined"
	)
	fun acceptIf(block: (Test) -> Boolean)

	@DSLMandatory(group = "constraint")
	fun denyIf(block: (Test) -> Boolean)

	fun fallbackToPrimitive(fallbackProvider: (Test) -> Test)

	fun fallbackTo(
		fallbackProvider: (Test) -> Fallback
	)
}

internal class ConstraintBuilder<Test, Fallback> private constructor(
	var name: String? = null,
	private var adapter: () -> ((Test) -> Fallback)
) : Constraint<Test, Fallback> {

	var verifiers: MutableList<(Test) -> Boolean> = mutableListOf()
	var fallbackProvider: ((Test) -> Fallback)? = null

	companion object {
		internal operator fun <T, S> invoke(
			name: String? = null,
			adapter: () -> ((T) -> S),
			block: ConstraintBuilder<T, S>.() -> Unit
		) =
			ConstraintBuilder(name, adapter).apply(block)
	}

	override fun acceptIf(block: (Test) -> Boolean) {
		verifiers.add(block)
	}

	override fun denyIf(block: (Test) -> Boolean) {
		verifiers.add { !block(it) }
	}

	override var fallbackToPrimitive: Test
		@Deprecated("", level = DeprecationLevel.ERROR)
		get() = throw UnsupportedOperationException()
		set(value) {
			fallbackToPrimitive { value }
		}

	override fun fallbackToPrimitive(fallbackProvider: (Test) -> Test) {
		fallbackTo { adapter()(fallbackProvider(it)) }
	}

	override var fallbackTo: Fallback
		@Deprecated("", level = DeprecationLevel.ERROR)
		get() = throw UnsupportedOperationException()
		set(value) {
			fallbackTo { value }
		}

	override fun fallbackTo(
		fallbackProvider: (Test) -> Fallback
	) {
		this.fallbackProvider = fallbackProvider
	}
}