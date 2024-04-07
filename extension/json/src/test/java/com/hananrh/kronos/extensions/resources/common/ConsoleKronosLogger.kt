package com.hananrh.kronos.extensions.resources.common

import com.hananrh.kronos.logging.KronosLogger

class ConsoleKronosLogger : KronosLogger {

	override fun v(msg: String) {
		log("v", msg)
	}

	override fun d(msg: String) {
		log("d", msg)
	}

	override fun w(msg: String) {
		log("w", msg)
	}

	override fun i(msg: String) {
		log("i", msg)
	}

	override fun e(msg: String, e: Exception?) {
		log("e", msg)
		e?.printStackTrace()
	}

	private fun log(lvl: String, msg: String) {
		println("$lvl: $msg")
	}
}