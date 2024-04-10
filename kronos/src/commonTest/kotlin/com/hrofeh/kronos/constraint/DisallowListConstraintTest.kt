package com.hrofeh.kronos.constraint

import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.common.initKronos
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.constraint.disallowList
import com.hrofeh.kronos.config.intConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object DisallowListConstraintTest : FunSpec({

	beforeSpec {
		initKronos()
	}

	class Config : KronosConfig by mapConfig() {
		val someInt by intConfig {
			default = 2
			cached = false
			disallowList = listOf(1, 3, 5)
		}
	}

	val config = Config()

	test("Should return remote value when not in disallow list") {
		withRemoteMap("someInt" to 4)

		config.someInt shouldBeEqual 4
	}

	test("Should return default value when remote value in disallow list") {
		withRemoteMap("someInt" to 3)

		config.someInt shouldBeEqual 2
	}
})