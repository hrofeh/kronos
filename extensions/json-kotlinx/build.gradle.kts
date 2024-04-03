plugins {
	id("org.jetbrains.kotlin.plugin.serialization")
	id("sdk-module")
}

apply(from = "../../versions.gradle")
apply(from = rootProject.file("gradle/mvn_push.gradle"))

dependencies {
	implementation(project(":extensions:json"))
	api(libs.kotlinx.serialization)
}