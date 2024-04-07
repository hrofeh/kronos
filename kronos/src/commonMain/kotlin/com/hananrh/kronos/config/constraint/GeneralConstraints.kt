package com.hananrh.kronos.config.constraint

import com.hananrh.kronos.config.Config

public var <T> Config<T, *>.allowlist: Iterable<T>
	@Deprecated("", level = DeprecationLevel.ERROR)
	get() = throw UnsupportedOperationException()
	set(value) {
		generalConstraint("allowlist") { it in value }
	}

public var <T> Config<T, *>.disallowList: Iterable<T>
	@Deprecated("", level = DeprecationLevel.ERROR)
	get() = throw UnsupportedOperationException()
	set(value) {
		generalConstraint("disallow") { it !in value }
	}


private fun <T, S> Config<T, S>.generalConstraint(name: String, allowBlock: (T) -> Boolean) {
	constraint(name) {
		acceptIf(allowBlock)
	}
}