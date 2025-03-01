package com.hrofeh.kronos.constraint

import com.hrofeh.kronos.common.Label
import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.intConfig
import com.hrofeh.kronos.config.type.typedConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object CustomConstraintTest : Spek(com.hrofeh.kronos.common.kronosTest {

    describe("Custom constraint should control acceptable remote values") {

        class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {
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
            com.hrofeh.kronos.common.withRemoteMap("someEvenOnlyInt" to 2)

            assertEquals(2, config.someEvenOnlyInt)
        }

        it("Should return remote value when valid by constraint - typedConfig") {
            com.hrofeh.kronos.common.withRemoteMap("someNotEmptyLabel" to Label("hello"))

            assertEquals(Label("hello"), config.someNotEmptyLabel)
        }

        it("Should return defined fallback value when remote value not valid by constraint") {
            com.hrofeh.kronos.common.withRemoteMap("someEvenOnlyInt" to 3)

            assertEquals(4, config.someEvenOnlyInt)
        }

        it("Should return defined fallback value when remote value not valid by constraint - typedConfig") {
            com.hrofeh.kronos.common.withRemoteMap("someNotEmptyLabel" to Label(""))

            assertEquals(Label("fallback"), config.someNotEmptyLabel)
        }
    }
})