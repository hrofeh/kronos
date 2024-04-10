plugins {
	id("org.jetbrains.kotlin.plugin.serialization")
	id("kmp-sdk-module")
	id("maven")
}

kotlin {
	sourceSets {
		commonMain.dependencies {
			implementation(project(":extension-json"))
			api(libs.kotlinx.serialization)
		}
	}
}