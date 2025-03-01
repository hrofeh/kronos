package com.hrofeh.kronos.getValue

import com.hrofeh.kronos.Kronos
import com.hrofeh.kronos.common.Label
import com.hrofeh.kronos.common.MapSource
import com.hrofeh.kronos.common.kronosTest
import com.hrofeh.kronos.common.mapConfig
import com.hrofeh.kronos.common.withRemoteMap
import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.booleanConfig
import com.hrofeh.kronos.config.type.floatConfig
import com.hrofeh.kronos.config.type.intConfig
import com.hrofeh.kronos.config.type.longConfig
import com.hrofeh.kronos.config.type.stringConfig
import com.hrofeh.kronos.config.type.typedConfig
import com.hrofeh.kronos.source.SourceDefinition
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.Random
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

object GetCache : Spek(com.hrofeh.kronos.common.kronosTest(cached = true) {

    describe("Cached config should calculate value only once") {

        class CacheConfig : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {
            val someInt by intConfig {
                default = 1
            }
            val someLong by longConfig {
                default = 1
            }
            val someFloat by floatConfig {
                default = 1f
            }
            val someString by stringConfig {
                default = ""
            }
            val someTyped by typedConfig<Label> {
                default = Label("default")
            }
        }

        val cacheConfig = CacheConfig()

        beforeGroup {
            recalcMap()
        }

        it("Should return original value after map is updated - intConfig") {
            val value = cacheConfig.someInt
            recalcMap()
            assertEquals(value, cacheConfig.someInt)
        }

        it("Should return original value after map is updated - longConfig") {
            val value = cacheConfig.someLong
            recalcMap()
            assertEquals(value, cacheConfig.someLong)
        }

        it("Should return original value after map is updated - floatConfig") {
            val value = cacheConfig.someFloat
            recalcMap()
            assertEquals(value, cacheConfig.someFloat)
        }

        it("Should return original value after map is updated - stringConfig") {
            val value = cacheConfig.someString
            recalcMap()
            assertEquals(value, cacheConfig.someString)
        }

        it("Should return original value after map is updated - typedConfig") {
            val value = cacheConfig.someTyped
            recalcMap()
            assertEquals(value, cacheConfig.someTyped)
        }
    }

    describe("Non cached config should resolve value on every field read") {

        class NoCacheConfig : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {
            val someInt by intConfig {
                default = 1
                cached = false
            }
            val someLong by longConfig {
                default = 1
                cached = false
            }
            val someFloat by floatConfig {
                default = 1f
                cached = false
            }
            val someString by stringConfig {
                default = ""
                cached = false
            }
            val someTyped by typedConfig<Label> {
                default = Label("")
                cached = false
            }
        }

        val nonCacheConfig = NoCacheConfig()

        beforeGroup {
            recalcMap()
        }

        it("Should return updated value after map is updated - intConfig") {
            val value = nonCacheConfig.someInt
            recalcMap()
            assertNotEquals(value, nonCacheConfig.someInt)
        }

        it("Should return updated value after map is updated - longConfig") {
            val value = nonCacheConfig.someLong
            recalcMap()
            assertNotEquals(value, nonCacheConfig.someLong)
        }

        it("Should return updated value after map is updated - floatConfig") {
            val value = nonCacheConfig.someFloat
            recalcMap()
            assertNotEquals(value, nonCacheConfig.someFloat)
        }

        it("Should return updated value after map is updated - stringConfig") {
            val value = nonCacheConfig.someString
            recalcMap()
            assertNotEquals(value, nonCacheConfig.someString)
        }

        it("Should return updated value after map is updated - typedConfig") {
            val value = nonCacheConfig.someTyped
            recalcMap()
            assertNotEquals(value, nonCacheConfig.someTyped)
        }
    }

    describe("Cached config should return new value if config source version is changed") {

        class Config : FeatureRemoteConfig by com.hrofeh.kronos.common.mapConfig() {
            val someInt by intConfig {
                default = 1
            }
            val someLong by longConfig {
                default = 1
            }
            val someFloat by floatConfig {
                default = 1f
            }
            val someString by stringConfig {
                default = ""
            }
            val someTyped by typedConfig<Label> {
                default = Label("")
            }
        }

        val config = Config()

        beforeGroup {
            recalcMap()
        }

        it("Should return updated value after map is updated - intConfig") {
            val value = config.someInt
            recalcMap(true)
            assertNotEquals(value, config.someInt)
        }

        it("Should return updated value after map is updated - longConfig") {
            val value = config.someLong
            recalcMap(true)
            assertNotEquals(value, config.someLong)
        }

        it("Should return updated value after map is updated - floatConfig") {
            val value = config.someFloat
            recalcMap(true)
            assertNotEquals(value, config.someFloat)
        }

        it("Should return updated value after map is updated - stringConfig") {
            val value = config.someString
            recalcMap(true)
            assertNotEquals(value, config.someString)
        }

        it("Should return updated value after map is updated - typedConfig") {
            val value = config.someTyped
            recalcMap(true)
            assertNotEquals(value, config.someTyped)
        }
    }
})

private fun recalcMap(bumpVersion: Boolean = false) {
    val random = Random()
    var version = Kronos.configSourceRepository.getSource(
        SourceDefinition.Class(MapSource::class)
    ).version
    if (bumpVersion) {
        version++
    }

    com.hrofeh.kronos.common.withRemoteMap(
        "someInt" to random.nextInt(),
        "someLong" to random.nextLong(),
        "someFloat" to random.nextFloat(),
        "someString" to "${random.nextLong()}",
        "someBoolean" to random.nextBoolean(),
        "someTyped" to Label(UUID.randomUUID().toString()),
        version = version
    )
}
