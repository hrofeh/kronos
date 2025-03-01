package com.hrofeh.kronos.extensions.core.configPropertyApi

import com.hrofeh.kronos.config.FeatureRemoteConfig
import com.hrofeh.kronos.config.type.intConfig
import com.hrofeh.kronos.extensions.core.asConfigProperty
import com.hrofeh.kronos.extensions.core.common.mapConfig
import com.hrofeh.kronos.extensions.core.common.withRemoteMap
import com.hrofeh.kronos.extensions.core.isRemotelyConfigured
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

			assertEquals(true, Config::someInt.asConfigProperty(config).isRemotelyConfigured())
		}
	}
})
