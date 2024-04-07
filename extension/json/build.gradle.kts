plugins{
    id("org.jetbrains.kotlin.plugin.serialization")
	id("kmp-sdk-module")
}

kotlin {
	sourceSets {
		commonTest.dependencies {
			implementation(project(":extension-json-kotlinx"))
		}
	}
}

//dependencies{
//	implementation(libs.dslint.annotations)
//}