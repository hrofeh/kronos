plugins {
	id("org.jetbrains.kotlin.plugin.serialization")
	id("kmp-sdk-module")
}

android {
	dependencies {
//		implementation(libs.dslint.annotations)
//		lintPublish(libs.dslint.checks)
//		testImplementation(project(":extension-resources"))

		testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
	}

	testOptions {
		unitTests.all {
			it.useJUnitPlatform()
		}
	}
}