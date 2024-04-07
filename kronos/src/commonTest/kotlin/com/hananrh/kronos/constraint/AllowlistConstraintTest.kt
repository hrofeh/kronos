package com.hananrh.kronos.constraint

import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.config.constraint.allowlist
import com.hananrh.kronos.config.intConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object AllowlistConstraintTest : FunSpec({

	class Config : KronosConfig by mapConfig() {
		val someInt by intConfig {
			default = 1
			cached = false
			allowlist = listOf(1, 3, 5)
		}
	}

	val config = Config()

	test("Should return remote value when in allowlist") {
		withRemoteMap("someInt" to 5)

		config.someInt shouldBeEqual 5
	}

	test("Should return default value when remote value not in allowlist") {
		withRemoteMap("someInt" to 2)

		config.someInt shouldBeEqual 1
	}
})