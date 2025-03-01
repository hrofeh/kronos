package com.hrofeh.kronos

import com.hrofeh.kronos.source.ConfigSource

internal class TestSource : ConfigSource {

    override fun getInteger(key: String) = null

    override fun putInteger(key: String, value: Int?) {
    }

    override fun getLong(key: String) = null

    override fun putLong(key: String, value: Long?) {
    }

    override fun getFloat(key: String) = null

    override fun putFloat(key: String, value: Float?) {
    }

    override fun getBoolean(key: String) = null

    override fun putBoolean(key: String, value: Boolean?) {
    }

    override fun getString(key: String) = null

    override fun putString(key: String, value: String?) {
    }

    override fun getStringSet(key: String) = null

    override fun putStringSet(key: String, value: Set<String>?) {
    }

    override fun getAny(key: String) = null

    override fun putAny(key: String, value: Any?) {
    }
}