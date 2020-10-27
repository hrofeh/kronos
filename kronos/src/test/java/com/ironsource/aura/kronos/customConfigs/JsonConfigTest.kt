package com.ironsource.aura.kronos.customConfigs

import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.common.Label
import com.ironsource.aura.kronos.common.kronosTest
import com.ironsource.aura.kronos.common.mapConfig
import com.ironsource.aura.kronos.common.withRemoteMap
import com.ironsource.aura.kronos.config.type.jsonConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

class LabelList : ArrayList<Label>()

object JsonConfigTest : Spek(kronosTest {

    class Config : FeatureRemoteConfig by mapConfig() {
        var someJson by jsonConfig<LabelList> {
            cached = false
            default = LabelList().apply { add(Label("default")) }
        }
    }

    val config = Config()

    describe("Json config field get") {

        it("Should return remote json value when valid") {
            withRemoteMap(
                    "someJson" to "[{val:\"remote\"}, {val:\"remote2\"}]"
            )

            assertEquals(listOf(Label("remote"), Label("remote2")),
                    config.someJson)
        }

        it("Should return default value whe json value invalid") {
            withRemoteMap(
                    "someJson" to "stam"
            )

            assertEquals(listOf(Label("default")), config.someJson)

            withRemoteMap(
                    "someJson" to 5
            )

            assertEquals(listOf(Label("default")), config.someJson)
        }
    }

    describe("Json config field set") {

        it("Should return set json value") {
            config.someJson = LabelList().apply { add(Label("set")) }
            assertEquals(listOf(Label("set")), config.someJson)
        }
    }
})
