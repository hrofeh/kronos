//package com.ironsource.aura.kronos.configPropertyApi
//
//import com.ironsource.aura.kronos.config.FeatureRemoteConfig
//import com.ironsource.aura.kronos.common.mapConfig
//import com.ironsource.aura.kronos.common.withRemoteMap
//import com.ironsource.aura.kronos.config.asConfigProperty
//import com.ironsource.aura.kronos.config.type.intConfig
//import org.spekframework.spek2.Spek
//import org.spekframework.spek2.style.specification.describe
//import kotlin.test.assertEquals
//
//object ConfigPropertyApiIsConfiguredTest : Spek({
//
//    class Config : FeatureRemoteConfig by mapConfig() {
//        val someInt by intConfig {
//            process { it + 1 }
//        }
//    }
//
//    val config = Config()
//
//    describe("Delegate isConfigured should return true for configured values") {
//
//        it("Should return true") {
//            withRemoteMap("someInt" to 1)
//
//            assertEquals(true, Config::someInt.asConfigProperty(config).isConfigured())
//        }
//    }
//})
