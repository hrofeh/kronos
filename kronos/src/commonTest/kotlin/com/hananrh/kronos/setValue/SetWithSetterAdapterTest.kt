package com.hananrh.kronos.setValue

import com.hananrh.kronos.KronosConfig
import com.hananrh.kronos.common.Label
import com.hananrh.kronos.common.initKronos
import com.hananrh.kronos.common.mapConfig
import com.hananrh.kronos.config.adaptedStringConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

object SetWithSetterAdapterTest : FunSpec({

	beforeSpec {
		initKronos()
	}

	class Config : KronosConfig by mapConfig() {
		var someLabel by adaptedStringConfig<Label> {
			default = Label("default")
			adapt {
				get { Label(it) }
				set { it.value }
			}
		}
	}

	val config = Config()

	test("Should return serialized set value") {
		config.someLabel = Label("test")
		config.someLabel shouldBeEqual Label("test")
		config.someLabel = Label("test2")
		config.someLabel shouldBeEqual Label("test2")
	}
})
