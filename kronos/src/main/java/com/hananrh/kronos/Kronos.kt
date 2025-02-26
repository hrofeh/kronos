package com.hananrh.kronos

import android.annotation.SuppressLint
import android.content.Context
import com.hananrh.dslint.annotations.DSLMandatory
import com.hananrh.dslint.annotations.DSLint
import com.hananrh.kronos.config.type.util.JsonConverter
import com.hananrh.kronos.logging.AndroidLogger
import com.hananrh.kronos.logging.Logger
import com.hananrh.kronos.source.ConfigSource
import com.hananrh.kronos.source.ConfigSourceRepository
import com.hananrh.kronos.source.SourceDefinition
import kotlin.reflect.KClass

/**
 * Kronos SDK entry point.
 */
@SuppressLint("StaticFieldLeak")
object Kronos {

    private var initialized = Kronos::context.isInitialized

    lateinit var context: Context
        private set

    internal lateinit var defaultOptions: DefaultOptions
        private set

    var logger: Logger? = null
        private set

    var jsonConverter: JsonConverter? = null
        get() {
            checkNotNull(
                field
            ) { "No json converter available, a converter needs to be supplied in Kronos.init()" }
            return field
        }
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
        if (initialized) {
            return
        }

        val options = OptionsBuilder(block)

        context = options.context.applicationContext

        defaultOptions = options.defaultOptions

        if (options.loggingOptions.enabled) {
            logger = options.loggingOptions.logger
        }

        if (options.hasJsonConverter()) {
            jsonConverter = options.jsonConverter
        }

        configSourceRepository = options.configSourceRepository
    }
}

/**
 * Configuration class used to initialize the SDK.
 *
 * @see Kronos.init
 */
@DSLint
interface Options {

    @set:DSLMandatory
    var context: Context

    /**
     * Json converter to be used with jsonConfig
     */
    var jsonConverter: JsonConverter

    /**
     * Define SDK logging options
     */
    fun logging(block: LoggingOptions.() -> Unit)

    /**
     * Define defaults for all configs.
     */
    fun defaultOptions(block: DefaultOptions.() -> Unit)

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
}

private class OptionsBuilder : Options {

    companion object {

        operator fun invoke(block: Options.() -> Unit) = OptionsBuilder().apply(
            block
        )
    }

    override lateinit var context: Context

    override lateinit var jsonConverter: JsonConverter

    val configSourceRepository = ConfigSourceRepository()

    fun hasJsonConverter() = ::jsonConverter.isInitialized

    var loggingOptions: LoggingOptions = LoggingOptionsBuilder {}

    var defaultOptions: DefaultOptions = DefaultOptionsBuilder {}

    override fun logging(block: LoggingOptions.() -> Unit) {
        loggingOptions = LoggingOptionsBuilder(block)
    }

    override fun defaultOptions(block: DefaultOptions.() -> Unit) {
        defaultOptions = DefaultOptionsBuilder(block)
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

        operator fun invoke(block: LoggingOptionsBuilder.() -> Unit) =
            LoggingOptionsBuilder().apply(
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

interface DefaultOptions {

    /**
     * Set whether configs are cached by default, can be overridden per config.
     */
    var cachedConfigs: Boolean
}

private class DefaultOptionsBuilder : DefaultOptions {

    companion object {
        operator fun invoke(block: DefaultOptionsBuilder.() -> Unit) =
            DefaultOptionsBuilder().apply(block)
    }

    override var cachedConfigs = false
}