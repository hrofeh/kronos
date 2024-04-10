import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	alias(libs.plugins.androidLibrary) apply false
	alias(libs.plugins.kotlin.serialization) apply false
	alias(libs.plugins.junit5) apply false
	alias(libs.plugins.kotlin.android) apply false
	alias(libs.plugins.androidApplication) apply false
	alias(libs.plugins.kotlinMultiplatform) apply false
}

allprojects {
	tasks.withType<KotlinCompile>().configureEach {
		kotlinOptions {
			jvmTarget = "1.8"
		}
	}
}

tasks.register("publishToMavenCentral"){
	dependsOn(":kronos:publishAllPublicationsToMavenCentralRepository")
	dependsOn(":extension-json:publishAllPublicationsToMavenCentralRepository")
	dependsOn(":extension-json-kotlinx:publishAllPublicationsToMavenCentralRepository")
}