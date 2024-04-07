package com.hananrh.kronos

import com.hananrh.kronos.common.MapSource2
import com.hananrh.kronos.common.initKronos
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.common.withRemoteMap2
import com.hananrh.kronos.config.intConfig
import com.hananrh.kronos.source.SourceDefinition
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object SourceTest : FunSpec({

	beforeSpec {
		initKronos("someInt" to 1)
	}

	class Config : KronosConfig by mapConfig() {

		val classSourceInt by intConfig {
			key = "someInt"
			default = 1
		}

		val specificSourceInt by intConfig {
			key = "someInt"
			sourceDefinition = SourceDefinition.Class(MapSource2::class)
			default = 1
		}
	}

	val config = Config()

	test("Should inherit source from class when no source set for config") {
		config.classSourceInt shouldBeEqual 1
	}

	test("Should use source set in config and ignore class source") {
		withRemoteMap2("someInt" to 2)

		config.specificSourceInt shouldBeEqual 2
	}
})
