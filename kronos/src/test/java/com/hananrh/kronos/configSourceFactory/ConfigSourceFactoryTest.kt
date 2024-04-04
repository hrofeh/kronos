package com.hananrh.kronos.configSourceFactory

import com.hananrh.kronos.Kronos
import com.hananrh.kronos.common.ConsoleLogger
import com.hananrh.kronos.common.MapSource
import com.hananrh.kronos.config.FeatureRemoteConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.source.identifiableTypedSource
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ConfigSourceFactoryTest : Spek({

	beforeGroup {
		Kronos.init {
			logging {
				logger = ConsoleLogger()
			}
			configSourceFactory<MapSource, String>(MapSource::class) {
				MapSource(it, mutableMapOf("prefixSomeInt" to 1))
			}
		}
	}

	describe("Using config source with prefix") {

		class Config : FeatureRemoteConfig {

			override val sourceDefinition = identifiableTypedSource<MapSource, String>("prefix")

			val someInt by intConfig {
				default = 0
			}
		}

		val config = Config()

		it("Should return configured value with prefix") {
			assertEquals(1, config.someInt)
		}
	}
})
