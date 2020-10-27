package com.ironsource.aura.kronos.config.type.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class RemoteIntValue(val value: Int)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class RemoteStringValue(val value: String)