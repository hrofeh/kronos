plugins {
	id("org.jetbrains.kotlin.plugin.serialization")
	id("sdk-module")
}

dependencies {
	implementation(libs.dslint.annotations)

	// Temp disabled until DSLint version that supports multi-module libraries
//	lintPublish(libs.dslint.checks)

	testImplementation(project(":extension:resources"))
}