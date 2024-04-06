package com.hananrh.kronos.extensions.resources

import android.os.Build
import com.hananrh.dslint.annotations.DSLExtension
import com.hananrh.kronos.config.Config
import kotlin.reflect.typeOf

val kronosResource
	get() = if (ResourcesExtension.isResourcesInitialized()) ResourcesExtension.resources else
		throw IllegalStateException("Kronos resources extension not initialized, did you forget to initialize it when calling Kronos.init()?")

@DSLExtension("primitiveDefault")
inline fun <reified T> Config<T, *>.defaultRes(id: Int) = primitiveDefault {
	when (typeOf<T>()) {
		typeOf<Int>() -> kronosResource.getInteger(id) as T
		typeOf<Long>() -> kronosResource.getInteger(id).toLong() as T
		typeOf<Float>() -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			kronosResource.getFloat(id) as T
		} else {
			kronosResource.getInteger(id).toFloat() as T
		}

		typeOf<String>() -> kronosResource.getString(id) as T
		typeOf<Boolean>() -> kronosResource.getBoolean(id) as T
		typeOf<Set<String>>() -> kronosResource.getStringArray(id).toSet() as T
		else -> {
			throw IllegalArgumentException("Unsupported config type")
		}
	}
}