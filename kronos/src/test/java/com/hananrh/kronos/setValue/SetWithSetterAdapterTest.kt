package com.hananrh.kronos.setValue

import com.hananrh.kronos.common.Label
import com.hananrh.kronos.common.kronosTest
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.adaptedStringConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.reflect.KClass
import kotlin.test.assertEquals

object SetWithSetterAdapterTest : Spek(kronosTest {

    describe("Simple config field set should return set value") {

        class Config : FeatureRemoteConfig by mapConfig() {
            var someLabel by adaptedStringConfig<Label> {
                default = Label("default")
                adapt {
                    get { Label(it) }
                    set { it.value }
                }
            }
        }

        val config = Config()

        it("Should return serialized set value") {
            config.someLabel = Label("test")
            assertEquals(Label("test"), config.someLabel)
            config.someLabel = Label("test2")
            assertEquals(Label("test2"), config.someLabel)
        }
    }
})
