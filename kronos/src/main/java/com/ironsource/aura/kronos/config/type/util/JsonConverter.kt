package com.ironsource.aura.kronos.config.type.util

import com.ironsource.aura.kronos.utils.Response
import java.lang.reflect.Type

interface JsonConverter {

    fun toJson(obj: Any?,
               type: Type): String?

    fun <T> fromJson(json: String?,
                     type: Type): Response<T, JsonException>
}

class JsonException(cause: Throwable) : Exception(cause)