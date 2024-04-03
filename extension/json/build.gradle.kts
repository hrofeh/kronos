plugins{
    id("org.jetbrains.kotlin.plugin.serialization")
	id("sdk-module")
}

dependencies{
	testImplementation(project(":extension:json-kotlinx"))
}