package com.hrofeh.kronos

import com.hrofeh.kronos.source.ConfigSource

internal class TestSource : ConfigSource {

    override fun getInteger(key: String) = null

    override fun getLong(key: String) = null

    override fun getFloat(key: String) = null

    override fun getBoolean(key: String) = null

    override fun getString(key: String) = null

    override fun getStringSet(key: String) = null

    override fun getAny(key: String) = null
}