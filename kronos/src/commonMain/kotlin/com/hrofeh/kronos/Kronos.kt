package com.hrofeh.kronos

import com.hrofeh.kronos.logging.KronosLogger
import com.hrofeh.kronos.source.ConfigSource
import com.hrofeh.kronos.source.ConfigSourceRepository
import com.hrofeh.kronos.source.SourceDefinition
import kotlin.reflect.KClass

/**
 * Kronos SDK entry point.
 */
public object Kronos {

	public var logger: KronosLogger? = null
		private set

	/**
	 * Returns config sources repository through which [ConfigSource]
	 * can be added, removed and retrieved.
	 *
	 * @return config source repository.
	 * @throws IllegalStateException if SDK is not initialized.
	 */
	public lateinit var configSourceRepository: ConfigSourceRepository
		private set

	/**
	 * Initializes the SDK with configuration
	 */
	public fun init(block: Options.() -> Unit) {
		val options = OptionsBuilder(block)

		if (options.loggingOptions.enabled) {
			logger = options.loggingOptions.logger
		}

		configSourceRepository = options.configSourceRepository
	}
}

public object ExtensionsOptions

/**
 * Configuration class used to initialize the SDK.
 *
 * @see Kronos.init
 */
public interface Options {

	/**
	 * Define SDK logging options
	 */
	public fun logging(block: LoggingOptions.() -> Unit)

	/**
	 * Add a config source.
	 * A config can have only one instance of the same class.
	 * Use configSourceFactory to support multiple instances for the same source.
	 */
	public fun configSource(configSource: () -> ConfigSource)

	/**
	 * Add a config source factory.
	 */
	public fun <S : ConfigSource, T : Any> configSourceFactory(
		sourceClass: KClass<S>,
		configSourceFactory: ConfigSourceRepository.ConfigSourceFactory<S, T>
	)

	/**
	 * Configure Kronos extensions
	 */
	public fun extensions(block: ExtensionsOptions.() -> Unit)
}

private class OptionsBuilder : Options {

	companion object {
		operator fun invoke(block: Options.() -> Unit) = OptionsBuilder().apply(
			block
		)
	}

	override fun extensions(block: ExtensionsOptions.() -> Unit) {
		ExtensionsOptions.apply(block)
	}

	val configSourceRepository = ConfigSourceRepository()

	var loggingOptions: LoggingOptions = LoggingOptionsBuilder {}

	override fun logging(block: LoggingOptions.() -> Unit) {
		loggingOptions = LoggingOptionsBuilder(block)
	}

	override fun configSource(configSource: () -> ConfigSource) {
		configSourceRepository.addSource(configSource())
	}

	override fun <S : ConfigSource, T : Any> configSourceFactory(
		sourceClass: KClass<S>,
		configSourceFactory: ConfigSourceRepository.ConfigSourceFactory<S, T>
	) {
		configSourceRepository.addSourceFactory(
			SourceDefinition.Class(sourceClass),
			configSourceFactory
		)
	}
}

public interface LoggingOptions {

	/**
	 * Set whether SDK logging is enabled (true by default).
	 */
	public var enabled: Boolean

	/**
	 * Set a logger to be used by the SDK.
	 * If a logger is not supplied [com.hrofeh.kronos.logging.AndroidLogger] is used.
	 */
	public var logger: KronosLogger?
}

private class LoggingOptionsBuilder : LoggingOptions {

	companion object {

		operator fun invoke(block: LoggingOptionsBuilder.() -> Unit) = LoggingOptionsBuilder().apply(
			block
		)
	}

	/**
	 * Set whether SDK logging is enabled (true by default).
	 */
	override var enabled = true

	/**
	 * Set a logger to be used by the SDK.
	 */
	override var logger: KronosLogger? = null
}