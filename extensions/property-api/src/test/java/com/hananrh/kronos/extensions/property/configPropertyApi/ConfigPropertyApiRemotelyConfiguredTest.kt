package com.hananrh.kronos.extensions.property.configPropertyApi

import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.extensions.property.common.mapConfig
import com.hananrh.kronos.extensions.property.common.withRemoteMap
import com.hananrh.kronos.extensions.property.getConfigProperty
import com.hananrh.kronos.extensions.property.isConfigured
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ConfigPropertyApiRemotelyConfiguredTest : Spek({

	class Config : FeatureRemoteConfig by mapConfig() {
		val someInt by intConfig {
			process { it + 1 }
		}
	}

	val config = Config()

	describe("Delegate isConfigured should return true for configured values") {

		it("Should return true") {
			withRemoteMap("someInt" to 1)

			assertEquals(true, config.getConfigProperty(Config::someInt).isConfigured())
		}
	}
})