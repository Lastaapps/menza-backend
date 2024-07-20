package cz.lastaapps

import cz.lastaapps.extensions.alias
import cz.lastaapps.extensions.libs
import cz.lastaapps.extensions.multiplatform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

class BaseKMPConvention : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            alias(libs.plugins.kotlin.multiplatform)
            alias(libs.plugins.kotlin.serialization)
        }

        tasks.withType<Test> {
            useJUnitPlatform()
            systemProperties["kotest.framework.parallelism"] = 4
        }

        multiplatform {

            jvmToolchain {
                languageVersion.set(JavaLanguageVersion.of(libs.versions.java.jvmTarget.get().toInt()))
            }

            compilerOptions {
                languageVersion.set(KotlinVersion.fromVersion(libs.versions.kotlin.languageVersion.get()))
                apiVersion.set(KotlinVersion.fromVersion(libs.versions.kotlin.languageVersion.get()))
            }

            jvm {
                testRuns.getByName("test").executionTask.configure {
                    useJUnitPlatform()
                    systemProperties["kotest.framework.parallelism"] = 4
                }
            }

            sourceSets.apply {
                getByName("commonMain") {
                    dependencies {
                        api(libs.kotlinx.dateTime)
                        api(libs.kotlinx.collection)
                        api(libs.kotlinx.serializationJson)
                        api(libs.kmLogging)
                        api(libs.koin.core)
                    }
                }
                getByName("commonTest") {
                    dependencies {
                        implementation(kotlin("test"))
                        implementation(libs.kotest.assertion)
                    }
                }
                getByName("jvmMain") {
                    dependencies {
                        implementation(libs.logback)
                    }
                }
                getByName("jvmTest") {
                    dependencies {
                        implementation(project.dependencies.platform(libs.junit5.bom))
                        implementation(libs.junit5.jupiter.api)

                        implementation(libs.kotlin.coroutines.test)
                        implementation(libs.kotest.jUnit5runner)
                    }
                }
            }
        }
    }
}
