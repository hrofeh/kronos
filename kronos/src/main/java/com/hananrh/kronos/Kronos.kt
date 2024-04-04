package com.hananrh.kronos

import android.annotation.SuppressLint
import com.hananrh.kronos.logging.AndroidLogger
import com.hananrh.kronos.logging.Logger
import com.hananrh.kronos.source.ConfigSource
import com.hananrh.kronos.source.ConfigSourceRepository
import com.hananrh.kronos.source.SourceDefinition
import com.ironsource.aura.dslint.annotations.DSLint
import kotlin.reflect.KClass

/**
 * Kronos SDK entry point.
 */
@SuppressLint("StaticFieldLeak")
object Kronos {

	var logger: Logger? = null
		private set

	/**
	 * Returns config sources repository through which [ConfigSource]
	 * can be added, removed and retrieved.
	 *
	 * @return config source repository.
	 * @throws IllegalStateException if SDK is not initialized.
	 */
	lateinit var configSourceRepository: ConfigSourceRepository
		private set

	/**
	 * Initializes the SDK with configuration
	 */
	fun init(block: Options.() -> Unit) {
		val options = OptionsBuilder(block)

		if (options.loggingOptions.enabled) {
			logger = options.loggingOptions.logger
		}

		configSourceRepository = options.configSourceRepository
	}
}

object ExtensionsOptions

/**
 * Configuration class used to initialize the SDK.
 *
 * @see Kronos.init
 */
@DSLint
interface Options {

	/**
	 * Define SDK logging options
	 */
	fun logging(block: LoggingOptions.() -> Unit)

	/**
	 * Add a config source.
	 * A config can have only one instance of the same class.
	 * Use configSourceFactory to support multiple instances for the same source.
	 */
	fun configSource(configSource: () -> ConfigSource)

	/**
	 * Add a config source factory.
	 */
	fun <S : ConfigSource, T : Any> configSourceFactory(
		sourceClass: KClass<S>,
		configSourceFactory: ConfigSourceRepository.ConfigSourceFactory<S, T>
	)

	/**
	 * Configure Kronos extensions
	 */
	fun extensions(block: ExtensionsOptions.() -> Unit)
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

interface LoggingOptions {

	/**
	 * Set whether SDK logging is enabled (true by default).
	 */
	var enabled: Boolean

	/**
	 * Set a logger to be used by the SDK.
	 * If a logger is not supplied [com.hananrh.kronos.logging.AndroidLogger] is used.
	 */
	var logger: Logger
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
	 * If a logger is not supplied [com.hananrh.kronos.logging.AndroidLogger] is used.
	 */
	override var logger: Logger = AndroidLogger()
}