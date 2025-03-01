package com.hrofeh.kronos.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Label(
	@SerialName("val")
	val value: String = ""
)