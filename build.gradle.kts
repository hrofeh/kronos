import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	alias(libs.plugins.androidLibrary) apply false
	alias(libs.plugins.kotlin.serialization) apply false
	alias(libs.plugins.junit5) apply false
	alias(libs.plugins.mvnPublish) apply false
	alias(libs.plugins.kotlin.android) apply false
}

allprojects {
	tasks.withType<KotlinCompile>().configureEach {
		kotlinOptions {
			jvmTarget = "1.8"
		}
	}
}