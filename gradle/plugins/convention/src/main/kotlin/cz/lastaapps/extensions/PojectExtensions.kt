package cz.lastaapps.extensions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.the
import org.gradle.plugin.use.PluginDependency
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

val Project.libs get() = the<org.gradle.accessors.dm.LibrariesForLibs>()

fun PluginManager.alias(plugin: Provider<PluginDependency>) {
    apply(plugin.get().pluginId)
}

val Project.multiplatformExtension: KotlinMultiplatformExtension
    get() = kotlinExtension as KotlinMultiplatformExtension

val Project.jvmExtension: KotlinJvmProjectExtension
    get() = kotlinExtension as KotlinJvmProjectExtension

fun Project.multiplatform(block: KotlinMultiplatformExtension.() -> Unit) {
    multiplatformExtension.apply(block)
}

fun Project.kotlinJvm(block: KotlinJvmProjectExtension.() -> Unit) {
    jvmExtension.apply(block)
}

fun Project.pluginManager(block: PluginManager.() -> Unit) {
    pluginManager.apply(block)
}

fun Project.java(block: JavaPluginExtension.() -> Unit) {
    extension("java", block)
}

inline fun <reified T : Any> Project.extension(
    name: String,
    block: Action<T>,
) {
    extensions.configure(name, block)
//    extensions.findByName(name)?.let { it as? T }?.apply(block)
//        ?: error("Extension $name missing")
}
