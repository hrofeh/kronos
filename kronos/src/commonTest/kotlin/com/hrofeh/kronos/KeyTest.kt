package com.hrofeh.kronos

import com.hrofeh.kronos.common.initKronos
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.config.intConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object KeyTest : FunSpec({

	beforeSpec {
		initKronos("someInt" to 1)
	}

	class Config : KronosConfig by mapConfig() {
		val someInt by intConfig {
			default = 0
		}

		val someIntWithKey by intConfig {
			key = "someInt"
			default = 0
		}
	}

	val config = Config()

	test("Should use key by property name when key not set") {
		config.someInt shouldBeEqual 1
	}

	test("Should use user provided key when set") {
		config.someIntWithKey shouldBeEqual 1
	}
})
