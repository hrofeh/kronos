import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension

class MavenConventionPlugin : Plugin<Project> {

	override fun apply(project: Project) = with(project) {
		pluginManager.apply {
			apply("maven-publish")
			apply("signing")
		}

		val javadocJar = tasks.registering(Jar::class) {
			archiveClassifier.set("javadoc")
		}

		extensions.configure<PublishingExtension> {
			publications {
				val signingTasks = tasks.withType<Sign>()
				tasks.withType<AbstractPublishToMaven>().configureEach {
					mustRunAfter(signingTasks)
				}

				repositories {
					maven {
						name = "mavenCentral"
						val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
						val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
						setUrl { if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl }
						credentials {
							username = extra["SONATYPE_USERNAME"].toString()
							password = extra["SONATYPE_PASSWORD"].toString()
						}
					}
				}
				publications {
					withType<MavenPublication> {
//						artifact(javadocJar)
						pom {
							name.set("Kronos")
							description.set("Kotlin Multiplatform Remote Config Management Library")
							licenses {
								license {
									name.set("MIT")
									url.set("https://opensource.org/licenses/MIT")
								}
							}
							url.set("https://github.com/hrofeh/Kronos")
							issueManagement {
								system.set("Github")
								url.set("https://github.com/hrofeh/Kronos/issues")
							}
							scm {
								connection.set("https://github.com/hrofeh/Kronos.git")
								url.set("https://github.com/hrofeh/Kronos")
							}
							developers {
								developer {
									name.set("Hanan Rofe Haim")
									email.set("hanan.rofe.haim@gmail.com")
								}
							}
						}
					}
				}
			}
		}

		extensions.configure<SigningExtension> {
			useInMemoryPgpKeys(extra["GPG_SIGNING_KEY"].toString(), extra["PGP_PASSWORD"].toString())
			sign((extensions["publishing"] as PublishingExtension).publications)
		}
	}
}