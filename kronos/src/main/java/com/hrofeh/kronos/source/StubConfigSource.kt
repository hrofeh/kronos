package com.hrofeh.kronos.source

internal class StubConfigSource : ConfigSource {

    override fun getInteger(key: String) = null

    override fun getLong(key: String) = null

    override fun getFloat(key: String) = null

    override fun getBoolean(key: String) = null

    override fun getString(key: String) = null

    override fun getStringSet(key: String) = null

    override fun getAny(key: String) = null
}