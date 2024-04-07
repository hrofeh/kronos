import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
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

//					testImplementation("io.mockk:mockk:1.12.2")
//					testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.20")
//					testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.12")
//					testImplementation("org.spekframework.spek2:spek-runner-junit5:2.0.12")
//					testImplementation(project(":kronos"))
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