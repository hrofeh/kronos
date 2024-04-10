package com.hrofeh.kronos.configSourceFactory

import com.hrofeh.kronos.KronosConfig
import com.hrofeh.kronos.common.MapSource
import com.hrofeh.kronos.common.initKronos
import com.hrofeh.kronos.config.intConfig
import com.hrofeh.kronos.source.scopedSource
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object ScopedSourceTest : FunSpec({

	beforeSpec {
		initKronos()
	}

	class Config : KronosConfig {

		override val sourceDefinition = scopedSource(
			MapSource(map = mutableMapOf("someInt" to 1))
		)

		val someInt by intConfig {
			default = 0
		}
	}

	val config = Config()

	test("Should return configured value with scoped source") {
		config.someInt shouldBeEqual 1
	}
})
