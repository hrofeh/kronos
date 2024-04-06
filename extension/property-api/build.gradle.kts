plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
    id("sdk-module")
}

dependencies {
    implementation(libs.kotlin.reflect)

    testImplementation(project(":extension-json"))
}