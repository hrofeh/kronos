package com.hananrh.kronos.config.constraint

import com.hananrh.kronos.config.Config

public var <T> Config<T, *>.whitelist: Iterable<T>
	@Deprecated("", level = DeprecationLevel.ERROR)
	get() = throw UnsupportedOperationException()
	set(value) {
		generalConstraint("whitelist") { it in value }
	}

public var <T> Config<T, *>.blacklist: Iterable<T>
	@Deprecated("", level = DeprecationLevel.ERROR)
	get() = throw UnsupportedOperationException()
	set(value) {
		generalConstraint("blacklist") { it !in value }
	}


private fun <T, S> Config<T, S>.generalConstraint(name: String, allowBlock: (T) -> Boolean) {
	constraint(name) {
		acceptIf(allowBlock)
	}
}