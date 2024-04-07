package com.hananrh.kronos.constraint

import com.hananrh.kronos.common.Label
import com.hananrh.kronos.common.kronosTest
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap
import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.config.type.typedConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object CustomConstraintTest : Spek(kronosTest {

	describe("Custom constraint should control acceptable remote values") {

		class Config : KronosConfig by mapConfig() {
			val someEvenOnlyInt by intConfig {
				default = 2
				cached = false
				constraint {
					acceptIf { it % 2 == 0 }
					fallbackTo { it + 1 }
				}
			}

			val someNotEmptyLabel by typedConfig<Label> {
				cached = false
				default = Label("default")
				constraint {
					acceptIf { (it as Label).value.isNotEmpty() }
					fallbackTo { Label("fallback") }
				}
			}
		}

		val config = Config()

		it("Should return remote value when valid by constraint") {
			withRemoteMap("someEvenOnlyInt" to 2)

			assertEquals(2, config.someEvenOnlyInt)
		}

		it("Should return remote value when valid by constraint - typedConfig") {
			withRemoteMap("someNotEmptyLabel" to Label("hello"))

			assertEquals(Label("hello"), config.someNotEmptyLabel)
		}

		it("Should return defined fallback value when remote value not valid by constraint") {
			withRemoteMap("someEvenOnlyInt" to 3)

			assertEquals(4, config.someEvenOnlyInt)
		}

		it("Should return defined fallback value when remote value not valid by constraint - typedConfig") {
			withRemoteMap("someNotEmptyLabel" to Label(""))

			assertEquals(Label("fallback"), config.someNotEmptyLabel)
		}
	}
})