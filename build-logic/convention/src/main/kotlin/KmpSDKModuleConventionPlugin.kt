import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpSDKModuleConventionPlugin : Plugin<Project> {

	override fun apply(project: Project) = with(project) {
		val parentName = project.parent?.name ?: ""
		val moduleName = project.name

		group = "com.hananrh.kronos"

		pluginManager.apply {
			apply("com.android.library")
			apply("org.jetbrains.kotlin.multiplatform")
		}

		extensions.configure<KotlinMultiplatformExtension> {
			explicitApi = ExplicitApiMode.Strict

			iosX64()
			iosArm64()
			iosSimulatorArm64()

			androidTarget {
				compilations.forEach {
					it.kotlinOptions {
						jvmTarget = "1.8"
					}
				}
			}

			with(sourceSets) {
				commonMain.dependencies {
					if (moduleName != "kronos") {
						implementation(project(":kronos"))
					}
				}

				commonTest.dependencies {
					implementation("io.kotest:kotest-framework-engine:5.8.1")
					implementation("io.kotest:kotest-assertions-core:5.8.1")
//					implementation("io.mockk:mockk:1.13.10")
					if (moduleName != "kronos") {
						implementation(project(":kronos"))
					}
				}
			}
		}

		extensions.configure<LibraryExtension> {
			compileSdk = 34
			defaultConfig {
				minSdk = 21
			}

			namespace = if (moduleName == "kronos") "com.hananrh.kronos" else "com.hananrh.kronos.$parentName.${moduleName.replace("-", "_")}"

			compileOptions {
				sourceCompatibility = JavaVersion.VERSION_1_8
				targetCompatibility = JavaVersion.VERSION_1_8
			}
		}
	}
}