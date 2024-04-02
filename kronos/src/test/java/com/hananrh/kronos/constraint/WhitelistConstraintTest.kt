package com.hananrh.kronos.constraint

import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.constraint.whitelist
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.common.kronosTest
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object WhitelistConstraintTest : Spek(kronosTest {

	describe("Whitelist should control acceptable remote values") {

		class Config : FeatureRemoteConfig by mapConfig() {
			val someInt by intConfig {
				default = 1
				cached = false
				whitelist = listOf(1, 3, 5)
			}
		}

		val config = Config()

		it("Should return remote value when in whitelist") {
			withRemoteMap("someInt" to 5)

			assertEquals(5, config.someInt)
		}

		it("Should return default value when remote value not in whitelist") {
			withRemoteMap("someInt" to 2)

			assertEquals(1, config.someInt)
		}
	}
})