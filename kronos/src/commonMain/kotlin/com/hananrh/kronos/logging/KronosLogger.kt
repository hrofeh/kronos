package com.hananrh.kronos.logging

/**
 * Logger interface for supplying a custom logger to the SDK.
 *
 */
public interface KronosLogger {

	public fun v(msg: String)
	public fun d(msg: String)
	public fun w(msg: String)
	public fun i(msg: String)
	public fun e(msg: String, e: Exception? = null)
}