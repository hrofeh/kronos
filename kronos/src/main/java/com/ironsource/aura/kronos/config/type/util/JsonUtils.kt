package com.ironsource.aura.kronos.config.type.util

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

fun getParameterizedType(type: Type,
                         vararg genericParameterTypes: Type): Type = object : ParameterizedType {
    override fun getActualTypeArguments() = genericParameterTypes

    override fun getRawType() = type

    override fun getOwnerType() = null
}