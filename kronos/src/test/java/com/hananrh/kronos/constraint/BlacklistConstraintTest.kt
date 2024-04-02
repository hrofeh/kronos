package com.hananrh.kronos.constraint

import com.hananrh.kronos.common.kronosTest
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.constraint.blacklist
import com.hananrh.kronos.config.type.intConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object BlacklistConstraintTest : Spek(kronosTest {

	describe("Blacklist should control acceptable remote values") {

		class Config : FeatureRemoteConfig by mapConfig() {
			val someInt by intConfig {
				default = 2
				cached = false
				blacklist = listOf(1, 3, 5)
			}
		}

		val config = Config()

		it("Should return remote value when not in blacklist") {
			withRemoteMap("someInt" to 4)

			assertEquals(4, config.someInt)
		}

		it("Should return default value when remote value in blacklist") {
			withRemoteMap("someInt" to 3)

			assertEquals(2, config.someInt)
		}
	}
})