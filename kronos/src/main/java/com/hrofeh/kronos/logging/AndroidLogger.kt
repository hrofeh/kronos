package com.hrofeh.kronos.logging

import android.util.Log

private const val TAG = "Kronos"

/**
 * Default logger for the SDK, logs to logcat with "Kronos" tag.
 */
class AndroidLogger : Logger {

    override fun v(msg: String) {
        Log.v(TAG, msg)
    }

    override fun d(msg: String) {
        Log.d(TAG, msg)
    }

    override fun w(msg: String) {
        Log.w(TAG, msg)
    }

    override fun i(msg: String) {
        Log.i(TAG, msg)
    }

    override fun e(msg: String,
                   e: Exception?) {
        Log.e(TAG, msg)
        e?.printStackTrace()
    }
}