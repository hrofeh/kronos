plugins{
    id("org.jetbrains.kotlin.plugin.serialization")
	id("kmp-sdk-module")
	id("maven")
}

kotlin {
	sourceSets {
		commonTest.dependencies {
			implementation(project(":extension-json-kotlinx"))
		}
	}
}