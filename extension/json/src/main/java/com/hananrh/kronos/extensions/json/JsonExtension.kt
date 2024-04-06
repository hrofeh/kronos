package com.hananrh.kronos.extensions.json

import com.hananrh.dslint.annotations.DSLMandatory
import com.hananrh.dslint.annotations.DSLint
import com.hananrh.kronos.ExtensionsOptions

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

	fun isSerializerInitialized() = JsonExtension::serializer.isInitialized
}