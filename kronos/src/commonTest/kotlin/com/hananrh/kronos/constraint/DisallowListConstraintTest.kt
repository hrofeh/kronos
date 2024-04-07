package com.hananrh.kronos.constraint

import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.config.constraint.disallowList
import com.hananrh.kronos.config.intConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object DisallowListConstraintTest : FunSpec({

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