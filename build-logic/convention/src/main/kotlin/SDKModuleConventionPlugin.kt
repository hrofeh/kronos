import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.model.KotlinAndroidExtension

class SDKModuleConventionPlugin : Plugin<Project> {

	override fun apply(project: Project) = with(project) {
		val parentName = project.parent?.name ?: ""
		val moduleName = project.name

		group = "com.hrofeh.kronos"

		pluginManager.apply {
			apply("com.android.library")
			apply("kotlin-android")
			apply("de.mannodermaus.android-junit5")
		}

		extensions.configure<LibraryExtension> {
			compileSdk = 34

			defaultConfig {
				minSdk = 21

				consumerProguardFiles("proguard-consumer-rules.pro")
			}

			compileOptions {
				sourceCompatibility = JavaVersion.VERSION_1_8
				targetCompatibility = JavaVersion.VERSION_1_8
			}

			namespace = if (moduleName == "kronos") "com.hrofeh.kronos" else "com.hrofeh.kronos.$parentName.${moduleName.replace("-", "_")}"

			buildTypes {
				getByName("release") {
					isMinifyEnabled = true
					proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
				}
			}
		}

		dependencies {
			if (moduleName != "kronos") {
				add("implementation", project(":kronos"))
			}

			add("testImplementation", "io.mockk:mockk:1.12.2")
			add("testImplementation", "org.jetbrains.kotlin:kotlin-test:1.8.20")
			add("testImplementation", "org.spekframework.spek2:spek-dsl-jvm:2.0.12")
			add("testImplementation", "org.spekframework.spek2:spek-runner-junit5:2.0.12")
			add("testImplementation", project(":kronos"))
		}
	}
}