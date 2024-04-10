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
		register("kmp-sdk-module") {
			id = "kmp-sdk-module"
			implementationClass = "KmpSDKModuleConventionPlugin"
		}
		register("maven") {
			id = "maven"
			implementationClass = "MavenConventionPlugin"
		}
	}
}