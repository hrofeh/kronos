package com.hananrh.kronos.extensions.resources

import com.hananrh.kronos.config.Config
import kotlin.reflect.typeOf

val kronosResource
	get() = if (ResourcesExtension.isResourcesInitialized()) ResourcesExtension.resources else
		throw IllegalStateException("Kronos resources extension not initialized, did you forget to initialize it when calling Kronos.init()?")

inline var <reified T> Config<T, *>.defaultRes: Int
	@Deprecated("No getter for you!", level = DeprecationLevel.ERROR)
	get() = throw UnsupportedOperationException()
	set(id) = primitiveDefault {
		when (typeOf<T>()) {
			typeOf<Int>() -> kronosResource.getInteger(id) as T
			typeOf<Long>() -> kronosResource.getInteger(id).toLong() as T
			typeOf<Float>() -> kronosResource.getFloat(id) as T
			typeOf<String>() -> kronosResource.getString(id) as T
			typeOf<Boolean>() -> kronosResource.getBoolean(id) as T
			typeOf<Set<String>>() -> kronosResource.getStringArray(id).toSet() as T
			else -> {
				throw IllegalArgumentException("Unsupported config type")
			}
		}
	}