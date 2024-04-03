plugins {
	id("sdk-module")
}

dependencies {
	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.config)
}