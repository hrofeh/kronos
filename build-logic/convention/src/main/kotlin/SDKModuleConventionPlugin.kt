import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class SDKModuleConventionPlugin : Plugin<Project> {

	override fun apply(project: Project) = with(project) {
		val parentName = project.parent?.name ?: ""
		val moduleName = project.name

		pluginManager.apply {
			apply("com.android.library")
			apply ("kotlin-android")
			apply("de.mannodermaus.android-junit5")
		}

		extensions.configure<LibraryExtension> {
			compileSdk = 34

			defaultConfig {
				minSdk = 21

				consumerProguardFiles("proguard-consumer-rules.pro")
			}

			namespace = "com.hananrh.kronos.$parentName.${moduleName.replace("-", "_")}"

			buildTypes {
				getByName("release") {
					isMinifyEnabled = true
					proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
				}
			}
		}

		dependencies {
			add("implementation", project(":kronos"))
			add("implementation", "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.20")
			add("implementation", "com.ironsource.aura.dslint:dslint-annotations:1.0.3")

			add("testImplementation", "io.mockk:mockk:1.12.2")
			add("testImplementation", "org.jetbrains.kotlin:kotlin-test:1.8.20")
			add("testImplementation", "org.spekframework.spek2:spek-dsl-jvm:2.0.12")
			add("testImplementation", "org.spekframework.spek2:spek-runner-junit5:2.0.12")
			add("testImplementation", project(":kronos"))
		}
	}
}