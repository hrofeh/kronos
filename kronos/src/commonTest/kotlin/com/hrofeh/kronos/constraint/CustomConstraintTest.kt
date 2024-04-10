package com.hrofeh.kronos.constraint

import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.common.Label
import com.hrofeh.kronos.common.initKronos
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.intConfig
import com.hrofeh.kronos.config.typedConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object CustomConstraintTest : FunSpec({

	beforeSpec {
		initKronos()
	}

	class Config : KronosConfig by mapConfig() {
		val someEvenOnlyInt by intConfig {
			default = 2
			cached = false
			constraint {
				allowIf { it % 2 == 0 }
				fallbackTo { it + 1 }
			}
		}

		val someNotEmptyLabel by typedConfig<Label> {
			cached = false
			default = Label("default")
			constraint {
				allowIf { (it as Label).value.isNotEmpty() }
				fallbackTo { Label("fallback") }
			}
		}
	}

	val config = Config()

	test("Should return remote value when valid by constraint") {
		withRemoteMap("someEvenOnlyInt" to 2)

		config.someEvenOnlyInt shouldBeEqual 2
	}

	test("Should return remote value when valid by constraint - typedConfig") {
		withRemoteMap("someNotEmptyLabel" to Label("hello"))

		config.someNotEmptyLabel shouldBeEqual Label("hello")
	}

	test("Should return defined fallback value when remote value not valid by constraint") {
		withRemoteMap("someEvenOnlyInt" to 3)

		config.someEvenOnlyInt shouldBeEqual 4
	}

	test("Should return defined fallback value when remote value not valid by constraint - typedConfig") {
		withRemoteMap("someNotEmptyLabel" to Label(""))

		config.someNotEmptyLabel shouldBeEqual Label("fallback")
	}
})