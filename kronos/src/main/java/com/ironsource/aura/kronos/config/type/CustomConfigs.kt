@file:Suppress("unused")

package com.ironsource.aura.kronos.config.type

import android.content.res.Resources
import android.graphics.Color
import android.webkit.URLUtil
import com.ironsource.aura.kronos.Kronos
import com.ironsource.aura.kronos.config.*
import com.ironsource.aura.kronos.config.type.util.ColorInt
import com.ironsource.aura.kronos.utils.Fail
import com.ironsource.aura.kronos.utils.Success
import com.ironsource.aura.kronos.utils.getColorHex

fun FeatureRemoteConfig.urlConfig(block: SimpleConfig<String>.() -> Unit) =
        ConfigPropertyFactory.fromPrimitive(SourceTypeResolver.string(),
                validator = { URLUtil.isValidUrl(it) },
                block = block)

fun FeatureRemoteConfig.textConfig(block: SimpleConfig<String>.() -> Unit) =
        stringConfig(block)

inline fun <reified T> FeatureRemoteConfig.jsonConfig(noinline block: Config<String, T>.() -> Unit) =
        ConfigPropertyFactory.from(SourceTypeResolver.string(),
                validator = { it.isNotEmpty() },
                getterAdapter = {
                    val res = Kronos.jsonConverter!!.fromJson(it, T::class.java)
                    when (res) {
                        is Success -> res.value
                        is Fail -> {
                            Kronos.logger?.e("Failed to parse json: $it", res.exception)
                            null
                        }
                    }
                },
                setterAdapter = { Kronos.jsonConverter!!.toJson(it) },
                block = block
        )

fun FeatureRemoteConfig.colorConfig(block: Config<String, ColorInt>.() -> Unit) =
        ConfigPropertyFactory.from(SourceTypeResolver.string(
                resourcesResolver = ResourcesResolver(Resources::getColorHex)),
                validator = { it.isNotEmpty() },
                getterAdapter = {
                    try {
                        ColorInt(
                                Color.parseColor(it))
                    } catch (e: Exception) {
                        Kronos.logger?.e("Failed to parse color hex: $it", e)
                        null
                    }
                },
                setterAdapter = { "#" + Integer.toHexString(it.value) },
                block = block
        )