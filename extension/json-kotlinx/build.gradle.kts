plugins {
	id("org.jetbrains.kotlin.plugin.serialization")
	id("kmp-sdk-module")
}

kotlin {
	sourceSets {
		commonMain.dependencies {
			implementation(project(":extension-json"))
			api(libs.kotlinx.serialization)
		}
	}
}