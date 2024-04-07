package com.hananrh.kronos.configSourceFactory

import com.hananrh.kronos.Kronos
import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.common.ConsoleKronosLogger
import com.hananrh.kronos.common.MapSource
import com.hananrh.kronos.config.intConfig
import com.hananrh.kronos.source.identifiableTypedSource
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object ConfigSourceFactoryTest : FunSpec({

	beforeSpec {
		Kronos.init {
			logging {
				logger = ConsoleKronosLogger()
			}
			configSourceFactory<MapSource, String>(MapSource::class) {
				MapSource(it, mutableMapOf("prefixSomeInt" to 1))
			}
		}
	}

	class Config : KronosConfig {

		override val sourceDefinition = identifiableTypedSource<MapSource, String>("prefix")

		val someInt by intConfig {
			default = 0
		}
	}

	val config = Config()

	test("Should return configured value with prefix") {
		config.someInt shouldBeEqual 1
	}
})
