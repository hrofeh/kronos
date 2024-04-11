import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpSDKModuleConventionPlugin : Plugin<Project> {

	override fun apply(project: Project) = with(project) {
		val moduleName = project.name

		group = "com.hrofeh.kronos"
		version = "1.4.0-rc2"

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
				publishAllLibraryVariants()
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
					if (moduleName != "kronos") {
						implementation(project(":kronos"))
					}
				}

				this["androidUnitTest"].dependencies {
					implementation("io.kotest:kotest-runner-junit5:5.8.1")
				}
			}
		}

		extensions.configure<LibraryExtension> {
			compileSdk = 34
			defaultConfig {
				minSdk = 21
			}

			namespace = "com.hrofeh.kronos.${moduleName.replace("-", "_")}"

			compileOptions {
				sourceCompatibility = JavaVersion.VERSION_1_8
				targetCompatibility = JavaVersion.VERSION_1_8
			}

			testOptions {
				unitTests.all {
					it.useJUnitPlatform()
				}
			}
		}
	}
}