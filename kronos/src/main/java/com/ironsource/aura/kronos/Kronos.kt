package com.ironsource.aura.kronos

import android.annotation.SuppressLint
import android.content.Context
import com.ironsource.aura.dslint.annotations.DSLMandatory
import com.ironsource.aura.dslint.annotations.DSLint
import com.ironsource.aura.kronos.config.type.util.JsonConverter
import com.ironsource.aura.kronos.logging.AndroidLogger
import com.ironsource.aura.kronos.logging.Logger
import com.ironsource.aura.kronos.source.ConfigSource
import com.ironsource.aura.kronos.source.ConfigSourceRepository
import com.ironsource.aura.kronos.source.SourceDefinition
import kotlin.reflect.KClass

/**
 * Kronos SDK entry point.
 */
@SuppressLint("StaticFieldLeak")
object Kronos {

    private var initialized = ::context.isInitialized

    lateinit var context: Context
        private set

    var logger: Logger? = null
        private set

    var jsonConverter: JsonConverter? = null
        get() {
            checkNotNull(
                    field) { "No json converter available, a converter needs to be supplied in Kronos.init()" }
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

        this.context = options.context.applicationContext

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
     * Add a config source.
     * A config can have only one instance of the same class.
     * Use configSourceFactory to support multiple instances for the same source.
     */
    fun configSource(configSource: () -> ConfigSource)

    /**
     * Add a config source factory.
     */
    fun <S : ConfigSource, T : Any> configSourceFactory(sourceClass: KClass<S>,
                                                        configSourceFactory: ConfigSourceRepository.ConfigSourceFactory<S, T>)
}

private class OptionsBuilder : Options {

    companion object {

        operator fun invoke(block: Options.() -> Unit) = OptionsBuilder().apply(
                block)
    }

    override lateinit var context: Context

    override lateinit var jsonConverter: JsonConverter

    internal val configSourceRepository = ConfigSourceRepository()

    internal fun hasJsonConverter() = ::jsonConverter.isInitialized

    internal var loggingOptions: LoggingOptions = LoggingOptionsBuilder {}

    override fun logging(block: LoggingOptions.() -> Unit) {
        loggingOptions = LoggingOptionsBuilder(block)
    }

    override fun configSource(configSource: () -> ConfigSource) {
        configSourceRepository.addSource(configSource())
    }

    override fun <S : ConfigSource, T : Any> configSourceFactory(sourceClass: KClass<S>,
                                                                 configSourceFactory: ConfigSourceRepository.ConfigSourceFactory<S, T>) {
        configSourceRepository.addSourceFactory(SourceDefinition.Class(sourceClass),
                configSourceFactory)
    }
}

interface LoggingOptions {

    /**
     * Set whether SDK logging is enabled (true by default).
     */
    var enabled: Boolean

    /**
     * Set a logger to be used by the SDK.
     * If a logger is not supplied [com.ironsource.aura.kronos.logging.AndroidLogger] is used.
     */
    var logger: Logger
}

private class LoggingOptionsBuilder : LoggingOptions {

    companion object {

        operator fun invoke(block: LoggingOptionsBuilder.() -> Unit) = LoggingOptionsBuilder().apply(
                block)
    }

    /**
     * Set whether SDK logging is enabled (true by default).
     */
    override var enabled = true

    /**
     * Set a logger to be used by the SDK.
     * If a logger is not supplied [com.ironsource.aura.kronos.logging.AndroidLogger] is used.
     */
    override var logger: Logger = AndroidLogger()
}