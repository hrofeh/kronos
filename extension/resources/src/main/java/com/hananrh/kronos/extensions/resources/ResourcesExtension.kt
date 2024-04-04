package com.hananrh.kronos.extensions.resources

import android.annotation.SuppressLint
import android.content.res.Resources
import com.hananrh.kronos.ExtensionsOptions
import com.ironsource.aura.dslint.annotations.DSLMandatory
import com.ironsource.aura.dslint.annotations.DSLint

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