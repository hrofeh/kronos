package com.hananrh.kronos.customConfigs

import com.hananrh.kronos.common.kronosTest
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.annotations.RemoteIntValue
import com.hananrh.kronos.config.type.annotations.RemoteStringValue
import com.hananrh.kronos.config.type.intEnumConfig
import com.hananrh.kronos.config.type.stringEnumConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

enum class Location {
	@RemoteIntValue(0)
	TOP,

	@RemoteIntValue(1)
	BOTTOM
}

enum class Size {
	@RemoteStringValue("s")
	SMALL,

	@RemoteStringValue("l")
	LARGE
}

object EnumConfigTest : Spek(kronosTest {

	class Config : FeatureRemoteConfig by mapConfig() {
		var someIntEnum by intEnumConfig<Location> {
			cached = false
			default = Location.TOP
		}

		var someStringEnum by stringEnumConfig<Size> {
			cached = false
			default = Size.SMALL
		}
	}

	val config = Config()

	describe("Enum config field get") {

		describe("Valid value") {
			beforeGroup {
				withRemoteMap(
					"someIntEnum" to 1,
					"someStringEnum" to "l"
				)
			}

			it("Should return remote value when corresponds to const") {
				assertEquals(Location.BOTTOM, config.someIntEnum)
			}

			it("Should return remote value when corresponds to const") {
				assertEquals(Size.LARGE, config.someStringEnum)
			}
		}

		describe("Value not corresponding to enum const") {
			beforeGroup {
				withRemoteMap(
					"someIntEnum" to 5,
					"someStringEnum" to "M"
				)
			}

			it("Should return default value when not corresponds to any const - intEnum") {
				assertEquals(Location.TOP, config.someIntEnum)
			}


			it("Should return default value when not corresponds to any const - stringEnm") {
				assertEquals(Size.SMALL, config.someStringEnum)
			}
		}

		describe("Invalid value type") {
			beforeGroup {
				withRemoteMap(
					"someIntEnum" to false,
					"someStringEnum" to 5
				)
			}

			it("Should return default value when not valid value type - intEnum") {
				assertEquals(Location.TOP, config.someIntEnum)
			}


			it("Should return default value when not valid value type - stringEnm") {
				assertEquals(Size.SMALL, config.someStringEnum)
			}
		}
	}

	describe("Enum config field set") {

		it("Should return set value - intEnum") {
			config.someIntEnum = Location.TOP
			assertEquals(Location.TOP, config.someIntEnum)
		}

		it("Should return set value - stringEnum") {
			config.someStringEnum = Size.SMALL
			assertEquals(Size.SMALL, config.someStringEnum)
		}
	}
})
