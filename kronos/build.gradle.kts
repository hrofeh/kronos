plugins {
	id("org.jetbrains.kotlin.plugin.serialization")
	id("sdk-module")
}

dependencies {
	implementation(libs.dslint.annotations)
	lintPublish(libs.dslint.checks)
}