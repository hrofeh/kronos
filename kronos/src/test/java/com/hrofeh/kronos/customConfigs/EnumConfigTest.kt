package com.hrofeh.kronos.customConfigs

import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.annotations.RemoteIntValue
import com.hrofeh.kronos.config.type.annotations.RemoteStringValue
import com.hrofeh.kronos.config.type.intEnumConfig
import com.hrofeh.kronos.config.type.stringEnumConfig
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

object EnumConfigTest : Spek(com.hrofeh.kronos.common.kronosTest {

	class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {
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
				com.hrofeh.kronos.common.withRemoteMap(
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
				com.hrofeh.kronos.common.withRemoteMap(
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
				com.hrofeh.kronos.common.withRemoteMap(
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
