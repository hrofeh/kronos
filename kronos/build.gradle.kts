plugins {
	id("org.jetbrains.kotlin.plugin.serialization")
	id("kmp-sdk-module")
	id("maven")
}

android {
	dependencies {
//		implementation(libs.dslint.annotations)
//		lintPublish(libs.dslint.checks)
//		testImplementation(project(":extension-resources"))
	}
}