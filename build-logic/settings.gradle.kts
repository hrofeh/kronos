pluginManagement {
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
	}
}
dependencyResolutionManagement {
	repositories {
		google()
		mavenCentral()
	}
	versionCatalogs {
		create("libs") {
			from(files("../gradle/kronos.versions.toml"))
		}
	}
}
rootProject.name = "build-logic"
include(":convention")