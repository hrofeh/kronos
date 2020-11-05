package com.ironsource.aura.kronos.converter.gson

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.ironsource.aura.kronos.config.type.util.JsonConverter
import com.ironsource.aura.kronos.config.type.util.JsonException
import com.ironsource.aura.kronos.utils.Fail
import com.ironsource.aura.kronos.utils.Response
import com.ironsource.aura.kronos.utils.Success
import java.lang.reflect.Type

class GsonConverter constructor(private val gson: Gson = Gson()) : JsonConverter {

    override fun toJson(obj: Any?,
                        type: Type): String? {
        return gson.toJson(obj)
    }

    override fun <T> fromJson(json: String?,
                              type: Type): Response<T, JsonException> {
        return try {
            Success(gson.fromJson(json, type))
        } catch (e: JsonSyntaxException) {
            Fail(JsonException(e))
        }
    }
}