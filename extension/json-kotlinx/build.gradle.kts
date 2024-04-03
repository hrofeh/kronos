plugins {
	id("org.jetbrains.kotlin.plugin.serialization")
	id("sdk-module")
}

dependencies {
	implementation(project(":extension:json"))
	api(libs.kotlinx.serialization)
}