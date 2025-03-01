package com.hrofeh.kronos.customConfigs

import com.hrofeh.kronos.common.Label
import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.jsonConfig
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object JsonConfigTest : Spek(com.hrofeh.kronos.common.kronosTest {

    class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {

        var someJsonList by jsonConfig<List<Label>> {
            cached = false
            default = listOf(Label("default"))
        }

        var someJson by jsonConfig<Label> {
            cached = false
            default = Label("default")
        }

        val someJsonMap by jsonConfig<Map<String, Label>> {
            cached = false
            default = mapOf("default" to Label("default"))
        }

    }

    val config = Config()

    describe("Json config field get") {

        it("Should return remote json value when valid") {
            com.hrofeh.kronos.common.withRemoteMap(
                "someJson" to "{\"val\":\"remote\"}",
                "someJsonList" to "[{\"val\":\"remote\"}, {\"val\":\"remote2\"}]",
                "someJsonMap" to "{\"remote\": {\"val\":\"remote\"}}"
            )

            assertEquals(
                Label("remote"),
                config.someJson
            )
            assertEquals(
                listOf(Label("remote"), Label("remote2")),
                config.someJsonList
            )
            assertEquals(
                mapOf("remote" to Label("remote")),
                config.someJsonMap
            )
        }

        it("Should return default value whe json value invalid") {
            com.hrofeh.kronos.common.withRemoteMap(
                "someJson" to "stam"
            )

            assertEquals(listOf(Label("default")), config.someJsonList)

            com.hrofeh.kronos.common.withRemoteMap(
                "someJson" to 5
            )

            assertEquals(listOf(Label("default")), config.someJsonList)
        }
    }

    describe("Json config field set") {

        it("Should return set json value") {
            config.someJsonList = listOf(Label("set"))
            assertEquals(listOf(Label("set")), config.someJsonList)
        }
    }
})
