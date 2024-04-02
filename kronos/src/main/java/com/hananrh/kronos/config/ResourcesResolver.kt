package com.hananrh.kronos.config

import android.content.res.Resources
import com.hananrh.kronos.utils.getAny
import com.hananrh.kronos.utils.getLong
import com.hananrh.kronos.utils.getStringSet

data class ResourcesResolver<T>(
	val resourcesGetter: Resources.(Int) -> T
) {

	companion object {
		val Int = ResourcesResolver(Resources::getInteger)
		val Long = ResourcesResolver(Resources::getLong)
		val Float = ResourcesResolver(Resources::getFloat)
		val Boolean = ResourcesResolver(Resources::getBoolean)
		val String = ResourcesResolver(Resources::getString)
		val StringSet = ResourcesResolver(Resources::getStringSet)
		val Any = ResourcesResolver(Resources::getAny)
	}
}