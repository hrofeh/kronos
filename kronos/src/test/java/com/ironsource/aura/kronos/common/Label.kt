package com.ironsource.aura.kronos.common

import com.google.gson.annotations.SerializedName

data class Label(
        @SerializedName("val")
        val value: String = "")