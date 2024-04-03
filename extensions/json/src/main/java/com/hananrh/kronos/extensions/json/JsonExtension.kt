package com.hananrh.kronos.extensions.json

import com.hananrh.kronos.ExtensionsOptions
import com.hananrh.kronos.extensions.json.util.JsonSerializer
import com.ironsource.aura.dslint.annotations.DSLMandatory
import com.ironsource.aura.dslint.annotations.DSLint

@Suppress("UnusedReceiverParameter")
fun ExtensionsOptions.json(block: JsonExtensionOptions.() -> Unit) {
	JsonExtension.apply {
		block()

		if (!isSerializerInitialized()) {
			throw IllegalStateException("Must supply json converter")
		}
	}
}

@DSLint
interface JsonExtensionOptions {

	/**
	 * Json serializer to be used with jsonConfig
	 */
	@set:DSLMandatory
	var serializer: JsonSerializer
}

internal object JsonExtension : JsonExtensionOptions {
	override lateinit var serializer: JsonSerializer

	fun isSerializerInitialized() = ::serializer.isInitialized
}