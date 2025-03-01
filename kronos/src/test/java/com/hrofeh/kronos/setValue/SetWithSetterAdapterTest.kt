package com.hrofeh.kronos.setValue

import com.hrofeh.kronos.common.Label
import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.adaptedStringConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.reflect.KClass
import kotlin.test.assertEquals

object SetWithSetterAdapterTest : Spek(com.hrofeh.kronos.common.kronosTest {

    describe("Simple config field set should return set value") {

        class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {
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
