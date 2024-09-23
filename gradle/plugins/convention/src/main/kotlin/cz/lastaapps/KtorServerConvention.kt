package cz.lastaapps

import cz.lastaapps.extensions.alias
import cz.lastaapps.extensions.implementation
import cz.lastaapps.extensions.kotlinJvm
import cz.lastaapps.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

class KtorServerConvention : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            pluginManager.apply {
                apply("org.gradle.application")
                alias(libs.plugins.kotlin.jvm)
                alias(libs.plugins.kotlin.serialization)
                alias(libs.plugins.java.kotlin)
                alias(libs.plugins.shadow)
            }

            kotlinJvm {
                jvmToolchain {
                    languageVersion.set(
                        JavaLanguageVersion.of(
                            libs.versions.java.jvmTarget
                                .get()
                                .toInt(),
                        ),
                    )
                }
                compilerOptions {
                    languageVersion.set(
                        KotlinVersion.fromVersion(
                            libs.versions.kotlin.languageVersion
                                .get(),
                        ),
                    )
                    apiVersion.set(
                        KotlinVersion.fromVersion(
                            libs.versions.kotlin.languageVersion
                                .get(),
                        ),
                    )
                }
            }

            dependencies {
                implementation(libs.kotlinx.dateTime)
                implementation(libs.kotlinx.collection)
                implementation(libs.kotlinx.serializationJson)

                implementation(libs.koin.core)
                implementation(libs.koin.ktorServer)

                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.cio)
                implementation(libs.ktor.server.auth)
                implementation(libs.ktor.server.autoHeadResponse)
                implementation(libs.ktor.server.callLogging)
                implementation(libs.ktor.server.compression)
                implementation(libs.ktor.server.conditionalsHeaders)
                implementation(libs.ktor.server.contentNegotiation)
                implementation(libs.ktor.server.defaultHeaders)
                implementation(libs.ktor.server.forwardedHeaders)
                implementation(libs.ktor.server.httpRedirect)
                implementation(libs.ktor.server.jsonSerialization)
                implementation(libs.ktor.server.statusPages)
                implementation(libs.ktor.server.sesstions)
            }
        }
}
