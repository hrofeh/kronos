package com.hrofeh.kronos.source

/**
 * Interface for config source.
 */
public interface ConfigSource {

	/**
	 * Return an Integer using the provided key.
	 * If no mapping exists for the key or the key value is not an Integer, return null.
	 *
	 * @param key          config key.
	 * @return the configured Integer for the key, null otherwise.
	 */
	public fun getInteger(key: String): Int?

	/**
	 * Return an Long using the provided key.
	 * If no mapping exists for the key or the key value is not an Long, return null.
	 *
	 * @param key          config key.
	 * @return the configured Long for the key, null otherwise.
	 */
	public fun getLong(key: String): Long?

	/**
	 * Return a Float using the provided key.
	 * If no mapping exists for the key or the key value is not a Float, return null.
	 *
	 * @param key          config key.
	 * @return the configured Float for the key, null otherwise.
	 */
	public fun getFloat(key: String): Float?

	/**
	 * Return a Boolean using the provided key.
	 * If no mapping exists for the key or the key value is not a Boolean, return null.
	 *
	 * @param key          config key.
	 * @return the configured Boolean for the key, null otherwise.
	 */
	public fun getBoolean(key: String): Boolean?

	/**
	 * Return a String using the provided key.
	 * If no mapping exists for the key or the key value is not a String, return null.
	 *
	 * @param key          config key.
	 * @return the configured String for the key, null otherwise.
	 */
	public fun getString(key: String): String?

	/**
	 * Return a String set using the provided key.
	 * If no mapping exists for the key or the key value is not a String, return null.
	 *
	 * @param key          config key.
	 * @return the configured String set for the key, null otherwise.
	 */
	public fun getStringSet(key: String): Set<String>?

	/**
	 * Return any value using the provided key.
	 * If no mapping exists for the key, return null.
	 *
	 * @param key          config key.
	 * @return the configured value for the key, null otherwise.
	 */
	public fun getAny(key: String): Any?
}