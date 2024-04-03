plugins {
	`kotlin-dsl`
}

dependencies {
	implementation(libs.android.gradle.plugin)
	implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
	plugins {
		register("sdk-module") {
			id = "sdk-module"
			implementationClass = "SDKModuleConventionPlugin"
		}
	}
}