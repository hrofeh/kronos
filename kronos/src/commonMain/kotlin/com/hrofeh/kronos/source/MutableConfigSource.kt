package com.hrofeh.kronos.source

/**
 * Interface for a mutable config source.
 */
public interface MutableConfigSource : ConfigSource {

	/**
	 * Override the mapping to the provided key with the provided value.
	 * Any subsequent calls to [.getInteger] will return this value.
	 *
	 * @param key   config key.
	 * @param value override value.
	 */
	public fun putInteger(key: String, value: Int?)

	/**
	 * Override the mapping to the provided key with the provided value.
	 * Any subsequent calls to [.getLong] will return this value.
	 *
	 * @param key   config key.
	 */
	public fun putLong(key: String, value: Long?)

	/**
	 * Override the mapping to the provided key with the provided value.
	 * Any subsequent calls to [.getFloat] will return this value.
	 *
	 * @param key   config key.
	 * @param value override value.
	 */
	public fun putFloat(key: String, value: Float?)

	/**
	 * Override the mapping to the provided key with the provided value.
	 * Any subsequent calls to [.getBoolean] will return this value.
	 *
	 * @param key   config key.
	 * @param value override value.
	 */
	public fun putBoolean(key: String, value: Boolean?)

	/**
	 * Override the mapping to the provided key with the provided value.
	 * Any subsequent calls to [.getString] will return this value.
	 *
	 * @param key   config key.
	 * @param value override value.
	 */
	public fun putString(key: String, value: String?)

	/**
	 * Override the mapping to the provided key with the provided value.
	 * Any subsequent calls to [.getStringSet] will return this value.
	 *
	 * @param key   config key.
	 * @param value override value.
	 */
	public fun putStringSet(key: String, value: Set<String>?)

	/**
	 * Override the mapping to the provided key with the provided value.
	 * Any subsequent calls to [.getAny] will return this value.
	 *
	 * @param key   config key.
	 * @param value override value.
	 */
	public fun putAny(key: String, value: Any?)
}