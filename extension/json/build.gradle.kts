plugins{
    id("org.jetbrains.kotlin.plugin.serialization")
	id("sdk-module")
}

dependencies{
	implementation(libs.dslint.annotations)

	testImplementation(project(":extension-json-kotlinx"))
}