package com.hrofeh.kronos.extension.json

import com.hrofeh.kronos.ExtensionsOptions

@Suppress("UnusedReceiverParameter")
public fun ExtensionsOptions.json(block: JsonExtensionOptions.() -> Unit) {
	JsonExtension.apply {
		block()

		if (!isSerializerInitialized()) {
			throw IllegalStateException("Must supply json converter")
		}
	}
}

public interface JsonExtensionOptions {

	/**
	 * Json serializer to be used with jsonConfig
	 */
	public var serializer: KronosJsonSerializer
}

internal object JsonExtension : JsonExtensionOptions {
	override lateinit var serializer: KronosJsonSerializer

	fun isSerializerInitialized() = JsonExtension::serializer.isInitialized
}