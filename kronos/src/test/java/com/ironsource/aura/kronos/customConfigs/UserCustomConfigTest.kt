package com.ironsource.aura.kronos.customConfigs

import com.ironsource.aura.kronos.common.kronosTest
import com.ironsource.aura.kronos.common.mapConfig
import com.ironsource.aura.kronos.common.withRemoteMap
import com.ironsource.aura.kronos.config.Config
import com.ironsource.aura.kronos.config.ConfigPropertyFactory
import com.ironsource.aura.kronos.config.FeatureRemoteConfig
import com.ironsource.aura.kronos.config.SourceTypeResolver
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

data class Label(val value: String)

fun FeatureRemoteConfig.labelConfig(block: Config<String, Label>.() -> Unit) =
        ConfigPropertyFactory.from(SourceTypeResolver.string(),
                validator = { it.isNotEmpty() },
                getterAdapter = { Label(it) },
                setterAdapter = { it.value },
                block = block
        )

object UserCustomConfigTest : Spek(kronosTest {

    class Config : FeatureRemoteConfig by mapConfig() {
        var someLabel by labelConfig {
            default = Label("default")
            cached = false
        }
    }

    val config = Config()

    describe("Enum config field get") {

        it("Should return remote value when valid") {
            withRemoteMap(
                    "someLabel" to "remote"
            )

            assertEquals(Label("remote"), config.someLabel)
        }

        it("Should return default value when invalid") {
            withRemoteMap(
                    "someLabel" to ""
            )

            assertEquals(Label("default"), config.someLabel)
        }
    }

    describe("Enum config field set") {

        it("Should return set value") {
            config.someLabel = Label("override")
            assertEquals(Label("override"), config.someLabel)
        }
    }
})

