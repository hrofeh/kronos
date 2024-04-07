package com.hananrh.kronos.configSourceFactory

import com.hananrh.kronos.Kronos
import com.hananrh.kronos.common.ConsoleKronosLogger
import com.hananrh.kronos.common.MapSource
import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.config.type.intConfig
import com.hananrh.kronos.source.identifiableTypedSource
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ConfigSourceFactoryTest : Spek({

	beforeGroup {
		Kronos.init {
			logging {
				logger = ConsoleKronosLogger()
			}
			configSourceFactory<MapSource, String>(MapSource::class) {
				MapSource(it, mutableMapOf("prefixSomeInt" to 1))
			}
		}
	}

	describe("Using config source with prefix") {

		class Config : KronosConfig {

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
