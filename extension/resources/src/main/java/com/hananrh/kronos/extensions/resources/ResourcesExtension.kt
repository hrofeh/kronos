package com.hananrh.kronos.extensions.resources

import android.annotation.SuppressLint
import android.content.res.Resources
import com.hananrh.dslint.annotations.DSLMandatory
import com.hananrh.dslint.annotations.DSLint
import com.hananrh.kronos.ExtensionsOptions

@Suppress("UnusedReceiverParameter")
@DSLMandatory
fun ExtensionsOptions.resources(block: ResourcesExtensionOptions.() -> Unit) {
	ResourcesExtension.apply {
		block()

		if (!isResourcesInitialized()) {
			throw IllegalStateException("Must supply resources object")
		}
	}
}

@DSLint
interface ResourcesExtensionOptions {

	@set:DSLMandatory
	var resources: Resources
}

@SuppressLint("StaticFieldLeak")
internal object ResourcesExtension : ResourcesExtensionOptions {
	override lateinit var resources: Resources

	fun isResourcesInitialized() = ::resources.isInitialized
}